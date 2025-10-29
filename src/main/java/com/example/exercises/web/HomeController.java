package com.example.exercises.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Exercise 1: Basic MVC with Thymeleaf
 * - "/" GET -> index.html (contains link to /contact)
 * - "/contact" GET -> contact.html (form), POST -> echo submitted info
 * - "/about" GET -> returns text directly
 * - Custom error handling for 404 and 405 via GlobalWebExceptionHandler
 */
@Controller
@RequestMapping
@Validated
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/contact")
    public String contactForm() {
        return "contact"; // templates/contact.html
    }

    public static class ContactForm {
        @NotBlank public String name;
        @Email @NotBlank public String email;
        @NotBlank public String message;
    }

    @PostMapping(value = "/contact", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitContact(@ModelAttribute ContactForm form, Model model) {
        model.addAttribute("form", form);
        return "contact_result"; // templates/contact_result.html
    }

    @GetMapping("/about")
    @ResponseBody
    public ResponseEntity<String> about() {
        return ResponseEntity.ok("About this site");
    }
}
