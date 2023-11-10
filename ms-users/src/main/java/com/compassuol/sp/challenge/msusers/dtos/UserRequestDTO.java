package com.compassuol.sp.challenge.msusers.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {


    @Size(min = 3, max = 255, message = "first name must have at least 3 characters")
    private String firstName;

    @Size(min = 3, max = 255, message = "last name must have at least 3 characters")
    private String lastName;

    @CPF
    private String cpf;

    private String birthdate;

    @Email(message = "Please provide a valid email")
    private String email;

    @Size(min = 6, max = 255, message = "password must have at least 6 characters")
    private String password;

    private Boolean active;

}
