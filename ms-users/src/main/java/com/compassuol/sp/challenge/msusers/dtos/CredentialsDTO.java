package com.compassuol.sp.challenge.msusers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsDTO {

    private String email;
    private String password;
}
