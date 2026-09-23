package com.hitesh.Task_Manager.controller;

import com.hitesh.Task_Manager.entity.User;
import com.hitesh.Task_Manager.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.hitesh.Task_Manager.dto.LoginRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    // Constructor injection
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
public String login(@RequestBody LoginRequest loginRequest) {

    boolean success = userService.loginUser(
            loginRequest.getEmail(),
            loginRequest.getPassword()
    );

    if (success) {
        return "Login successful";
    }

    return "Invalid email or password";
}
}