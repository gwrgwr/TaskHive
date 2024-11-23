package com.example.taskhive.services;

import com.example.taskhive.config.security.TokenService;
import com.example.taskhive.domain.user.*;
import com.example.taskhive.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthorizationService(UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUsersByUser(username);
    }

    public LoginResponseDTO login(LoginAuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((UserEntity) auth.getPrincipal());
        UserEntity user = (UserEntity) auth.getPrincipal();
        return new LoginResponseDTO(token, user.getId(), user.getName(), user.getEmail(), user.getRole().toString());
    }

    public RegisterResponseDTO registerAdmin(RegisterAuthenticationDTO data) throws BadRequestException {
        if (userRepository.findUsersByUser(data.user()) != null) {
            throw new BadRequestException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity user = new UserEntity(data.name(), data.user(), data.email(), encryptedPassword, UserRole.ADMIN);
        this.userRepository.save(user);
        return new RegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole().toString());
    }

    public RegisterResponseDTO register(RegisterAuthenticationDTO data) throws BadRequestException {
        if (userRepository.findUsersByUser(data.user()) != null && userRepository.findUsersByEmail(data.email()) != null) {
            throw new BadRequestException("Invalid Role");
        }
        UserRole role;
        try {
            role = UserRole.valueOf(data.role().toUpperCase());
            if (role == UserRole.ADMIN) {
                throw new BadRequestException("Invalid Role");
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Role");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity user = new UserEntity(data.name(), data.user(), data.email(), encryptedPassword, role);
        this.userRepository.save(user);
        return new RegisterResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole().toString());
    }
}
