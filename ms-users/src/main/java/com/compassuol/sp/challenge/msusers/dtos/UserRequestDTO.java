package com.compassuol.sp.challenge.msusers.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 3, max = 255, message = "last name must have at least 3 characters")
    private String lastName;

    @Column(nullable = false)
    @CPF
    private String cpf;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(nullable = false)
    @Size(min = 6, max = 255, message = "password name must have at least 6 characters")
    private String password;

    @Column(nullable = false)
    private Boolean active;

}
