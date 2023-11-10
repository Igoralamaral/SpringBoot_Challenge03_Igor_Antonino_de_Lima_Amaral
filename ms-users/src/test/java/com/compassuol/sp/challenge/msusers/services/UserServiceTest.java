package com.compassuol.sp.challenge.msusers.services;

import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.entities.User;
import com.compassuol.sp.challenge.msusers.factories.UserFactory;
import com.compassuol.sp.challenge.msusers.factories.UserResponseDTOFactory;
import com.compassuol.sp.challenge.msusers.repositories.UserRepository;
import com.compassuol.sp.challenge.msusers.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final Long ID = 1L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserFactory userFactory;

    @Mock
    private UserResponseDTOFactory userResponseDTOFactory;

    @Test
    void createUser_withValidData_thenReturnUser() throws IOException, ParseException {

        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);
        UserResponseDTO userResponseDTO = Utils.readFromFile("/json/userResponseDTO.json", UserResponseDTO.class);

        when(userRepository.save(any())).thenReturn(user);
        UserResponseDTO userResponse = userService.createUser(userRequestDTO);

        assertEquals(userResponseDTO, userResponse);
        verify(userRepository).save(any());
    }

    @Test
    public void createUser_withInvalidName_throwsRuntimeException() throws IOException {
        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        UserRequestDTO userInvalidRequestDTO = Utils.readFromFile("/json/userInvalidRequest.json", UserRequestDTO.class);

        when(userRepository.save(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> userService.createUser(userInvalidRequestDTO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void findUserById_withValidID_thenReturnUser() throws IOException {
        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        UserResponseDTO userResponseDTO = Utils.readFromFile("/json/userResponseDTO.json", UserResponseDTO.class);

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));

        UserResponseDTO userResponse = userService.getUserById(ID);

        assertEquals(userResponseDTO, userResponse);
        assertNotNull(userResponse);
        verify(userRepository).findById(any());
    }

    @Test
    void findUserById_withInvalidID_throwsNotFoundException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(ID));
    }

    @Test
    void updateUser_withValidID_thenReturnsUpdatedUser() throws IOException, ParseException {
        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);
        UserResponseDTO userResponseDTO = Utils.readFromFile("/json/userResponseDTO.json", UserResponseDTO.class);

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any())).thenReturn(user);

        UserResponseDTO userUpdated = userService.updateUserById(ID, userRequestDTO);

        assertEquals(userResponseDTO, userUpdated);
        verify(userRepository).findById(any());
        verify(userRepository).save(any());
    }

    @Test
    void updateUser_withInvalidID_throwsNoSuchElementException() throws IOException {
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.updateUserById(ID, userRequestDTO));
        verify(userRepository).findById(any());
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void updateUserPassword_WithValidId_returnsSuccess() throws IOException {
        User user = Utils.readFromFile("/json/userEntity.json", User.class);
        PasswordRequestDTO passwordRequestDTO = Utils.readFromFile("/json/passwordRequest.json", PasswordRequestDTO.class);
        PasswordSucessDTO passwordSucessDTO = Utils.readFromFile("/json/passwordResponse.json", PasswordSucessDTO.class);

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any())).thenReturn(user);

        PasswordSucessDTO userPasswordUpdated = userService.updatePassword(ID, passwordRequestDTO);

        assertEquals(passwordSucessDTO, userPasswordUpdated);
        verify(userRepository).findById(any());
        verify(userRepository).save(any());
    }
}
