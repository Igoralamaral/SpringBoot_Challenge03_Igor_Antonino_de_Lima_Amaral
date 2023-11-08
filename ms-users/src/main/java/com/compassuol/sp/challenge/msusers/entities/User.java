package com.compassuol.sp.challenge.msusers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 255, message = "first name must have at least 3 characters")
    private String firstName;

    @Column(nullable = false)
    @Size(min = 3, max = 255, message = "last name must have at least 3 characters")
    private String lastName;

    @Column(nullable = false)
    @CPF
    private String cpf;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(nullable = false)
    @Size(min = 6, max = 255, message = "password must have at least 6 characters")
    private String password;

    @Column(nullable = false)
    private Boolean active;

}
