package com.compassuol.sp.challenge.msusers.controllers;

import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.securityJwt.JwtAuthFilter;
import com.compassuol.sp.challenge.msusers.securityJwt.JwtService;
import com.compassuol.sp.challenge.msusers.services.UserService;
import com.compassuol.sp.challenge.msusers.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    public static final String CREATE_USER_URL = "/v1/users";
    public static final String LOGIN_URL = "/v1/login";
    public static final String GET_USER_BY_ID = "/v1/users/1";
    public static final String UPDATE_USER_BY_ID = "/v1/users/1";
    public static final String UPDATE_PASSWORD = "/v1/users/1/password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void createUser_success_returnsCreated() throws Exception {
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);

        when(userService.createUser(any())).thenReturn(UserResponseDTO.builder().build());

        String input = Utils.mapToString(userRequestDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(CREATE_USER_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createUser_exception_methodArgumentNotValid() throws Exception {
        UserRequestDTO userRequestDTO = Utils.readFromFile("/json/userInvalidRequest.json", UserRequestDTO.class);

        when(userService.createUser(any())).thenReturn(UserResponseDTO.builder().build());

        String input = Utils.mapToString(userRequestDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(CREATE_USER_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void setGetUserById_success_ReturnsUser() throws Exception {
        when(userService.getUserById(any())).thenReturn(UserResponseDTO.builder().build());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(GET_USER_BY_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void setGetUserById_exception_UserNotFoundException() throws Exception {
        when(userService.getUserById(any())).thenThrow(new NoSuchElementException());

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(GET_USER_BY_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void updatedUser_sucess_ReturnsUser() throws Exception {
        UserRequestDTO request = Utils.readFromFile("/json/userRequestDTO.json", UserRequestDTO.class);

        when(userService.updateUserById(any(), any())).thenReturn(UserResponseDTO.builder().build());

        String input = Utils.mapToString(request);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(UPDATE_USER_BY_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
