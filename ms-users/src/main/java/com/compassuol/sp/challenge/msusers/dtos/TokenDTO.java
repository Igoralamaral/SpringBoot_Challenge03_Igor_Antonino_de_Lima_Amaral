package com.compassuol.sp.challenge.msusers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

    private String email;
    private String token;
}
