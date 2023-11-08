package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class UserFactory {

    public static User createUserFromDTO(UserRequestDTO userRequestDTO) throws ParseException {

        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setCpf(userRequestDTO.getCpf());
        user.setEmail(userRequestDTO.getEmail());
        SimpleDateFormat birthday = new SimpleDateFormat("dd/MM/yyyy");
        var birthdayDate = birthday.parse(userRequestDTO.getBirthdate());
        user.setBirthdate(birthdayDate);
        user.setActive(userRequestDTO.getActive());

        return user;
    }

    public static User updateUser(UserRequestDTO userRequestDTO, User user) throws ParseException {
        user.setId(user.getId());
        user.setCpf(userRequestDTO.getCpf());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setActive(userRequestDTO.getActive());
        SimpleDateFormat birthday = new SimpleDateFormat("dd/MM/yyyy");
        var birthdayDate = birthday.parse(userRequestDTO.getBirthdate());
        user.setBirthdate(birthdayDate);
        user.setEmail(userRequestDTO.getEmail());

        return user;
    }
}
