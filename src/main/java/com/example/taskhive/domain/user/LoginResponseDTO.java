package com.example.taskhive.domain.user;


public record LoginResponseDTO(String token, String id, String name, String email, UserRole role) {
}
