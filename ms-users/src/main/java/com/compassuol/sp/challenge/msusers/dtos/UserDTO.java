package com.compassuol.sp.challenge.msusers.dtos;

import jakarta.persistence.Column;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public class UserDTO {

    @Column(nullable = false, length = 3)
    private String firstName;

    @Column(nullable = false, length = 3)
    private String lastName;

    @Column(nullable = false)
    @CPF
    private String cpf;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(nullable = false, length = 6)
    private String password;

    @Column(nullable = false)
    private boolean active;

}
