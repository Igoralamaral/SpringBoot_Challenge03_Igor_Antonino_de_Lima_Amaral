package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import com.compassuol.sp.challenge.msusers.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final Integer ID = 1;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserWithValidData_success() throws IOException, ParseException {

        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);
        UserResponseDTO userResponseDTO = Utils.readFromFile("/json/userResponseDTO.json", UserResponseDTO.class);

        when(userRepository.save(any())).thenReturn(user);
        UserResponseDTO userResponse = userService.createUser(userRequestDTO);

        assertEquals(userResponseDTO, userResponse);
        verify(userRepository).save(any());
    }

}
