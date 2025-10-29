package com.example.exercises;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Exercise Suite Application
 * - Exercise 1: Basic MVC with Thymeleaf (HomeController)
 * - Exercise 2: Employees Management (MongoDB + Thymeleaf)
 * - REST API: Accounts, Products, Orders (MongoDB + JWT + CORS)
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
