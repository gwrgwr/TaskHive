package com.example.taskhive.controllers;

import com.example.taskhive.annotations.user.LoginUserAnnotation;
import com.example.taskhive.domain.user.LoginRequestDTO;
import com.example.taskhive.domain.user.LoginResponseDTO;
import com.example.taskhive.services.AuthorizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "Auth operations")
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthorizationService authorizationService;

    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    @LoginUserAnnotation
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authorizationService.login(loginRequestDTO));
    }
}
