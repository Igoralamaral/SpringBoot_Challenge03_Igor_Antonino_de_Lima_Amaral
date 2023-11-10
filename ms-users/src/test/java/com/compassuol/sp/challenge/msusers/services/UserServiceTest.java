package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.factories.UserFactory;
import com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import com.compassuol.sp.challenge.msusers.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import static com.compassuol.sp.challenge.msusers.utils.Utils.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final Integer ID = 1;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserWithValidData_success() throws IOException, ParseException {
        User user = readFromFile("/ms-users/src/test/java/resources/json/userEntity.json", User.class);
        UserRequestDTO userRequestDTO = readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);
        UserResponseDTO userResponseDTO = readFromFile("/json/userResponseDTO.json", UserResponseDTO.class);

        when(userRepository.save(any())).thenReturn(user);
        UserResponseDTO userResponse = userService.createUser(userRequestDTO);

        assertEquals(userResponseDTO, userResponse);
        verify(userRepository).save(any());
    }

}
