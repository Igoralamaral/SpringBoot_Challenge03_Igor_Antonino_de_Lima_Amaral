package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.constants.RabbitMQConstants;
import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.enums.EventsEnum;
import com.compassuol.sp.challenge.msusers.exceptions.InvalidCredentials;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import com.compassuol.sp.challenge.msusers.securityJwt.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static com.compassuol.sp.challenge.msusers.factories.NotificationFactory.createNotification;
import static com.compassuol.sp.challenge.msusers.factories.UserFactory.createUserFromDTO;
import static com.compassuol.sp.challenge.msusers.factories.UserFactory.updateUser;
import static com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory.createResponseUserDTO;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private JwtService jwtService;

    private RabbitMQService rabbitMQService;

    UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RabbitMQService rabbitMQService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.rabbitMQService = rabbitMQService;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws ParseException {
        var user = createUserFromDTO(userRequestDTO);
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        user.setPassword(password);
        var userSaved = userRepository.save(user);
        var userResponseDTO = createResponseUserDTO(userSaved);
        var notification = createNotification(userResponseDTO.getEmail(), EventsEnum.CREATE, Date.from(Instant.now()));
        rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_CREATE, notification);
        return userResponseDTO;
    }

    public TokenDTO login(CredentialsDTO credentialsDTO) {
        try {
            User user = User.builder().email(credentialsDTO.getEmail()).password(credentialsDTO.getPassword()).build();
            UserDetails userAuthenticated = authenticate(user);
            String token = jwtService.tokenGenerate(user);
            var notification = createNotification(user.getEmail(), EventsEnum.LOGIN, Date.from(Instant.now()));
            rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_LOGIN, notification);
            return new TokenDTO(user.getEmail(), token);

        } catch (RuntimeException e) {
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
        var notification = createNotification(userSaved.getEmail(), EventsEnum.UPDATE, Date.from(Instant.now()));
        rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_UPDATE, notification);
        return userDTO;
    }

    @Transactional
    public PasswordSucessDTO updatePassword(Long id, PasswordRequestDTO passwordRequestDTO) {
        var user = userRepository.findById(id).get();
        String password = passwordEncoder.encode(passwordRequestDTO.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        var notification = createNotification(user.getEmail(), EventsEnum.UPDATE_PASSWORD, Date.from(Instant.now()));
        rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_UPDATE_PASSWORD, notification);
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
