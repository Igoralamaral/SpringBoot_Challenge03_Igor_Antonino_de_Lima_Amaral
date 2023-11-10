package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.exceptions.InvalidCredentials;
import com.compassuol.sp.challenge.msusers.factories.UserFactory;
import com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import com.compassuol.sp.challenge.msusers.securityJwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.compassuol.sp.challenge.msusers.factories.UserFactory.createUserFromDTO;
import static com.compassuol.sp.challenge.msusers.factories.UserFactory.updateUser;
import static com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory.createResponseUserDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private JwtService jwtService;

    UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws ParseException {
        var user = createUserFromDTO(userRequestDTO);
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        user.setPassword(password);
        var userSaved = userRepository.save(user);
        var userResponseDTO = createResponseUserDTO(userSaved);
        return userResponseDTO;
    }

    public TokenDTO login(CredentialsDTO credentialsDTO) {
        try {
            User user = User.builder().email(credentialsDTO.getEmail()).password(credentialsDTO.getPassword()).build();
            UserDetails userAuthenticated = authenticate(user);
            String token = jwtService.tokenGenerate(user);
            return new TokenDTO(user.getEmail(), token);

        }catch (RuntimeException e){
            throw new InvalidCredentials(e.getMessage());
        }
    }

    public UserResponseDTO getUserById(Long id) {
        var user = userRepository.findById(id).get();
        var userResponseDTO = createResponseUserDTO(user);
        return userResponseDTO;
    }

    @Transactional
    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequestDTO) throws ParseException {
        var user = userRepository.findById(id).get();
        var userUpdated = updateUser(userRequestDTO, user);
        var userSaved = userRepository.save(userUpdated);
        var userDTO = createResponseUserDTO(userSaved);
        return userDTO;
    }

    @Transactional
    public PasswordSucessDTO updatePassword(Long id, PasswordRequestDTO passwordRequestDTO) {
        var user = userRepository.findById(id).get();
        String password = passwordEncoder.encode(passwordRequestDTO.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        PasswordSucessDTO passwordSucessDTO = new PasswordSucessDTO("Password updated with success");
        return passwordSucessDTO;
    }
    
    public UserDetails authenticate(User user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        var equalsPassword = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());
        if (equalsPassword) {
            return userDetails;
        }

        throw new RuntimeException("Passwords is not equals");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
