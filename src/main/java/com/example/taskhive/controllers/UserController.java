package com.example.taskhive.controllers;

import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "Endpoint for user function administration")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public List<UserEntity> adminLogin() {
        return userService.getAllUsers();
    }
}
