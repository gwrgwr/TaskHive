package com.example.taskhive.controllers;

import com.example.taskhive.annotations.authorization.LoginAuthorizationAnnotation;
import com.example.taskhive.annotations.authorization.RegisterAdminAuthorizationAnnotation;
import com.example.taskhive.annotations.authorization.RegisterUserAuthorizationAnnotation;
import com.example.taskhive.config.security.TokenService;
import com.example.taskhive.domain.user.*;
import com.example.taskhive.repositories.UserRepository;
import com.example.taskhive.services.AuthorizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
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
@Tag(name = "Authorization", description = "Endpoint for connecting and registering users")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    @LoginAuthorizationAnnotation
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginAuthenticationDTO data) {
        return ResponseEntity.ok().body(authorizationService.login(data));
    }

    @PostMapping("admin/register")
    @RegisterAdminAuthorizationAnnotation
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterAuthenticationDTO data) {
        try {
            return ResponseEntity.ok().body(authorizationService.registerAdmin(data));
        } catch (BadRequestException exception) {
            throw new RuntimeException("Bad Request", exception);
        }
    }

    @PostMapping("/register")
    @RegisterUserAuthorizationAnnotation
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterAuthenticationDTO data) {
        try {
            return ResponseEntity.ok().body(authorizationService.register(data));
        } catch (BadRequestException exception) {
            throw new RuntimeException("Bad Request", exception);
        }
    }
}
