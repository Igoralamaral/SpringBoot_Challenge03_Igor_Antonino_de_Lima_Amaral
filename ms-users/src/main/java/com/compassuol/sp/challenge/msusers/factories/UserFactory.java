package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.entities.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
}
