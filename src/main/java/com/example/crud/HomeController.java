package com.example.crud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "forward:/login.html";
    }

    @GetMapping("/home")
    public String appHome() {
        return "forward:/index.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "forward:/contact.html";
    }
}


