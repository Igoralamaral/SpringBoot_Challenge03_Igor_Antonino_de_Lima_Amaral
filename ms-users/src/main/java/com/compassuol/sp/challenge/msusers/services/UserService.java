package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.CredentialsDTO;
import com.compassuol.sp.challenge.msusers.dtos.TokenDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.entities.User;
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
import static com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory.createResponseUserDTOFromOptional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws ParseException {
        var user = createUserFromDTO(userRequestDTO);
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        var userResponseDTO = createResponseUserDTO(user);
        return userResponseDTO;
    }

    public TokenDTO login(CredentialsDTO credentialsDTO) {
        try {
            User user = User.builder().email(credentialsDTO.getEmail()).password(credentialsDTO.getPassword()).build();
            UserDetails userAuthenticated = authenticate(user);
            String token = jwtService.tokenGenerate(user);
            return new TokenDTO(user.getEmail(), token);

        }catch (UsernameNotFoundException e){
            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException("Password invalid");
        }
    }

    public UserResponseDTO getUserById(Long id) {
        var user = userRepository.findById(id);
        if(user.isPresent()){
            var userResponseDTO = createResponseUserDTOFromOptional(user);
            return userResponseDTO;
        }

        throw new RuntimeException("User not found!");
    }

    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequestDTO) throws ParseException {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        var userUpdated = updateUser(userRequestDTO, user);
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        userUpdated.setPassword(password);
        userRepository.save(userUpdated);
        var userDTO = createResponseUserDTO(user);
        return userDTO;
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
