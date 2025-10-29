package com.example.crud.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository repository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", repository.findAll());
        return "employees/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/add";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/add";
        }
        repository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        var emp = repository.findById(id).orElse(null);
        if (emp == null) {
            return "redirect:/employees";
        }
        model.addAttribute("employee", emp);
        return "employees/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable String id, @ModelAttribute @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/edit";
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
