package com.example.taskhive.controllers;

import com.example.taskhive.domain.user.*;
import com.example.taskhive.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User", description = "User operations")
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/user/name/{userName}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserResponseDTO> getUserByName(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUserByName(userName));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        UserEntity user = this.userService.saveUser(registerRequestDTO);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO registerRequestDTO) {
        UserEntity user = this.userService.saveAdminUser(registerRequestDTO);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String id, @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }
}
