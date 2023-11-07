package com.compassuol.sp.challenge.msusers.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
