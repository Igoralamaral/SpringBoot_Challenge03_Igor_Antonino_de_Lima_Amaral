package com.compassuol.sp.challenge.msusers.controllers;

import com.compassuol.sp.challenge.msusers.dtos.*;
import com.compassuol.sp.challenge.msusers.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(userService.login(credentialsDTO));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) throws ParseException {
        return ResponseEntity.ok(userService.updateUserById(id, userRequestDTO));
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<PasswordSucessDTO> updatePassword(@PathVariable Long id, @RequestBody @Valid PasswordRequestDTO passwordRequestDTO) {
        return ResponseEntity.ok(userService.updatePassword(id, passwordRequestDTO));
    }
}
