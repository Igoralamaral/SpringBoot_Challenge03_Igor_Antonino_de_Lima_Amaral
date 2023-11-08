package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.entities.User;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class UserResponseDTOFactory {

    public static UserResponseDTO createResponseUserDTO(User user){

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setCpf(user.getCpf());
        userResponseDTO.setEmail(user.getEmail());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateFormated = dateFormat.format(user.getBirthdate());
        userResponseDTO.setBirthdate(dateFormated);
        userResponseDTO.setActive(user.getActive());

        return userResponseDTO;
    }
}
