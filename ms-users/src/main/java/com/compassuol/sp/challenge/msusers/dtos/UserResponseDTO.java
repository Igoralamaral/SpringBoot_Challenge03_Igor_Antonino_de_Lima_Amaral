package com.compassuol.sp.challenge.msusers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String cpf;
    private String email;
    private Boolean active;
}
