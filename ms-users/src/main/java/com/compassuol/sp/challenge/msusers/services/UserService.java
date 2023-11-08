package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.factories.UserFactory;
import com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.compassuol.sp.challenge.msusers.factories.UserFactory.createUserFromDTO;
import static com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory.createResponseUserDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws ParseException {
        var user = createUserFromDTO(userRequestDTO);
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        var userResponseDTO = createResponseUserDTO(user);
        return userResponseDTO;
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
