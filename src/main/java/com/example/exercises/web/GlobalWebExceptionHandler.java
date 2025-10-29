package com.example.exercises.web;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * Exercise 1: Custom web error pages (no Whitelabel)
 * - 404: show a friendly page for unknown URLs
 * - 405: show a specific page for wrong HTTP method
 */
@ControllerAdvice
public class GlobalWebExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Page not found");
        model.addAttribute("message", "The URL you visited does not exist: " + ex.getRequestURL());
        return "error"; // templates/error.html
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, Model model) {
        model.addAttribute("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        model.addAttribute("error", "Method not allowed");
        model.addAttribute("message", "Unsupported method: " + ex.getMethod());
        return "error"; // templates/error.html
    }
}
