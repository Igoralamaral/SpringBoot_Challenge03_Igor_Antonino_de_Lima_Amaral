package com.compassuol.sp.challenge.msusers.controllers;

import com.compassuol.sp.challenge.msusers.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private UserService userService;


}
