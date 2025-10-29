package com.example.crud.service;

import com.example.crud.dto.AuthResponse;
import com.example.crud.dto.UserLoginRequest;
import com.example.crud.dto.UserRegistrationRequest;
import com.example.crud.exception.ResourceAlreadyExistsException;
import com.example.crud.model.User;
import com.example.crud.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("demo")
public class DemoAuthService {

    @Autowired
    private Map<String, User> userStorage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AtomicLong idCounter;

    public AuthResponse register(UserRegistrationRequest request) {
        // Check if email already exists
        boolean emailExists = userStorage.values().stream()
                .anyMatch(user -> user.getEmail().equals(request.getEmail()));
        
        if (emailExists) {
            throw new ResourceAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        User user = User.builder()
                .id(String.valueOf(idCounter.getAndIncrement()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userStorage.put(user.getId(), user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public AuthResponse login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userStorage.values().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }
}