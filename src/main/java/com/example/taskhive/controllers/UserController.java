package com.example.taskhive.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Endpoint for user function administration")
public class UserController {
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "Admin login";
    }
}
