package com.example.exercises.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Account document for authentication.
 */
@Data
@NoArgsConstructor
@Document("accounts")
public class Account {
    @Id
    private String id;

    @Email
    @NotBlank
    @Indexed(unique = true)
    private String email;

    @NotBlank
    private String passwordHash;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
