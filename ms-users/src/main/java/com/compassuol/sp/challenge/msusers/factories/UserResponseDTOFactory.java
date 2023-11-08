package com.compassuol.sp.challenge.msusers.factories;

import com.compassuol.sp.challenge.msusers.dtos.UserRequestDTO;
import com.compassuol.sp.challenge.msusers.dtos.UserResponseDTO;
import com.compassuol.sp.challenge.msusers.entities.User;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;

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

    public static UserResponseDTO createResponseUserDTOFromOptional(Optional<User> user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.get().getId());
        userResponseDTO.setFirstName(user.get().getFirstName());
        userResponseDTO.setLastName(user.get().getLastName());
        userResponseDTO.setCpf(user.get().getCpf());
        userResponseDTO.setEmail(user.get().getEmail());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateFormated = dateFormat.format(user.get().getBirthdate());
        userResponseDTO.setBirthdate(dateFormated);
        userResponseDTO.setActive(user.get().getActive());

        return userResponseDTO;
    }
}
