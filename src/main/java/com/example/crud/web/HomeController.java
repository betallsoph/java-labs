package com.example.crud.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/contact")
    public String contactForm() {
        return "contact"; // templates/contact.html
    }

    @PostMapping("/contact")
    public String handleContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "") String message,
            Model model) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("message", message);
        return "contact_result"; // templates/contact_result.html
    }

    @GetMapping("/about")
    public ResponseEntity<String> about() {
        return ResponseEntity.ok("About this site");
    }
}
