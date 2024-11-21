package com.example.taskhive.controllers;

import com.example.taskhive.config.security.TokenService;
import com.example.taskhive.domain.user.*;
import com.example.taskhive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginAuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((Users) auth.getPrincipal());
        Users user = (Users) auth.getPrincipal();
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole().toString()));
    }

    @PostMapping("admin/register")
    public ResponseEntity registerAdmin(@RequestBody RegisterAuthenticationDTO data) {
        if (userRepository.findUsersByUser(data.user()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users user = new Users(data.name(), data.user(), data.email(), encryptedPassword, UserRole.ADMIN);
        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterAuthenticationDTO data) {
        if (userRepository.findUsersByUser(data.user()) != null && userRepository.findUsersByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        UserRole role;
        try {
            role = UserRole.valueOf(data.role().toUpperCase());
            if (role == UserRole.ADMIN) {
                return ResponseEntity.badRequest().body("Invalid role.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users user = new Users(data.name(), data.user(), data.email(), encryptedPassword, role);
        this.userRepository.save(user);
        return ResponseEntity.ok(new RegisterResponseDTO(user));
    }
}
