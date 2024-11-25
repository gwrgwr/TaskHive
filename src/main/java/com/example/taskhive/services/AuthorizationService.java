package com.example.taskhive.services;

import com.example.taskhive.domain.user.LoginRequestDTO;
import com.example.taskhive.domain.user.LoginResponseDTO;
import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.exceptions.user.UserNotFoundException;
import com.example.taskhive.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthorizationService {

    private final TokenService tokenService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthorizationService(TokenService tokenService, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var user = userService.getUserByNameEntity(loginRequestDTO.user());
        if (user == null || !passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new UserNotFoundException();
        }

        String token = tokenService.generateToken(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                AuthorityUtils.createAuthorityList(user.getRole().name())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
