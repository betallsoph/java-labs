package com.example.crud;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account")
public class AccountController {

    private final UserRepository userRepository;

    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @Operation(summary = "Register account")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = Optional.ofNullable(body.get("username")).orElse("").trim();
        String password = Optional.ofNullable(body.get("password")).orElse("").trim();
        if (username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username/password required");
        }
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password); // demo only
        userRepository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = Optional.ofNullable(body.get("username")).orElse("").trim();
        String password = Optional.ofNullable(body.get("password")).orElse("").trim();
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of("message", "ok")))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials"));
    }
}


