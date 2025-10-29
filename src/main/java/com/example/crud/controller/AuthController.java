package com.example.crud.controller;

import com.example.crud.dto.ApiResponse;
import com.example.crud.dto.AuthResponse;
import com.example.crud.dto.UserLoginRequest;
import com.example.crud.dto.UserRegistrationRequest;
import com.example.crud.service.AuthService;
import com.example.crud.service.DemoAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired(required = false)
    private AuthService authService;

    @Autowired(required = false)
    private DemoAuthService demoAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody UserRegistrationRequest request) {
        AuthResponse response;
        if (demoAuthService != null) {
            response = demoAuthService.register(request);
        } else {
            response = authService.register(request);
        }
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        AuthResponse response;
        if (demoAuthService != null) {
            response = demoAuthService.login(request);
        } else {
            response = authService.login(request);
        }
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}