package com.example.exercises.employees;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Exercise 2: Employee document stored in MongoDB
 */
@Data
@NoArgsConstructor
@Document("employees")
public class Employee {
    @Id
    private String id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String position;

    @PositiveOrZero
    private double salary;
}
