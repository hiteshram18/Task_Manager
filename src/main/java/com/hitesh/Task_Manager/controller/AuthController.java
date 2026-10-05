package com.hitesh.Task_Manager.controller;

import com.hitesh.Task_Manager.dto.LoginRequest;
import com.hitesh.Task_Manager.entity.User;
import com.hitesh.Task_Manager.security.JwtService;
import com.hitesh.Task_Manager.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    // Register user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.createUser(user);
    }

    // Login user
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        boolean success = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (!success) {
            return "Invalid email or password";
        }

        // Generate JWT using user's email
        String token = jwtService.generateToken(
                loginRequest.getEmail()
        );

        return token;
    }
}