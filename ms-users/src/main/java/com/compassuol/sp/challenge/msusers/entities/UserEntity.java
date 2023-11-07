package com.compassuol.sp.challenge.msusers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    private String firstName;

    @Column(nullable = false, length = 3)
    private String lastName;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false, unique = true)
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(nullable = false, length = 6)
    private String password;

    @Column(nullable = false)
    private boolean active;
}
