package com.laitusneo.backend.controller;

import com.laitusneo.backend.dto.LoginRequest;
import com.laitusneo.backend.dto.RegisterRequest;
import com.laitusneo.backend.dto.AuthResponse;
import com.laitusneo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return userService.login(request); // now returns token + username + role
    }
}
