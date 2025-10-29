package com.example.exercises.users;

import com.example.exercises.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST endpoints for account register/login.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Data
    public static class RegisterRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
        @NotBlank private String firstName;
        @NotBlank private String lastName;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (accountRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }
        Account acc = new Account();
        acc.setEmail(req.getEmail());
        acc.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        acc.setFirstName(req.getFirstName());
        acc.setLastName(req.getLastName());
        accountRepository.save(acc);
        return ResponseEntity.ok(Map.of("message", "Registered successfully"));
    }

    @Data
    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        String token = jwtService.generateToken(req.getEmail(), new HashMap<>());
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        return ResponseEntity.ok(body);
    }
}
