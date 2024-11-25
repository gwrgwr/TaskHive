package com.example.taskhive.domain.user;

public record RegisterRequestDTO(String name, String user, String password, String email, UserRole role) {}
