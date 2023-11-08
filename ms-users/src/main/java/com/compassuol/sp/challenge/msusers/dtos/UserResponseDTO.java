package com.compassuol.sp.challenge.msusers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserResponseDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String cpf;
    private String email;
    private Boolean active;
}
