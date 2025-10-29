package com.example.exercises.employees;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Exercise 2: Employees pages (Thymeleaf + MongoDB)
 * - /employees (GET): list all
 * - /employees (POST): not available (405 handled globally)
 * - /employees/add GET -> show form; POST -> save and redirect to /employees
 * - /employees/edit/{id} GET -> show form; POST -> update and redirect
 * - /employees/delete/{id} POST -> delete and redirect
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", repository.findAll());
        return "employees/list"; // templates/employees/list.html
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("mode", "add");
        return "employees/form"; // templates/employees/form.html
    }

    @PostMapping("/add")
    public String addSubmit(@Valid @ModelAttribute("employee") Employee employee,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/form";
        }
        repository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Optional<Employee> e = repository.findById(id);
        if (e.isEmpty()) {
            throw new IllegalArgumentException("Employee not found: " + id);
        }
        model.addAttribute("employee", e.get());
        model.addAttribute("mode", "edit");
        return "employees/form";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable String id,
                             @Valid @ModelAttribute("employee") Employee employee,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/form";
        }
        employee.setId(id);
        repository.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        repository.deleteById(id);
        return "redirect:/employees";
    }
}
