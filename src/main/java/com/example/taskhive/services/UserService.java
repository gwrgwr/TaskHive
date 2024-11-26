package com.example.taskhive.services;

import com.example.taskhive.domain.user.*;
import com.example.taskhive.exceptions.user.UserAlreadyExistsException;
import com.example.taskhive.exceptions.user.UserNotFoundException;
import com.example.taskhive.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserById(String userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public UserResponseDTO getUserByName(String userName) {
        UserEntity user = repository.findByUser(userName);
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public UserEntity getUserByNameEntity(String userName) {
        return repository.findByUser(userName);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = repository.findAll();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        for (UserEntity user : users) {
            userResponseDTOs.add(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()));
        }
        return userResponseDTOs;
    }

    public UserEntity saveUser(RegisterRequestDTO user) {
        UserEntity userEntity = repository.findByUser(user.name());
        if (userEntity != null) {
            throw new UserAlreadyExistsException();
        }
        return repository.save(new UserEntity(user.name(), user.user(), user.email(), passwordEncoder.encode(user.password()), user.role()));
    }

    public UserEntity saveAdminUser(RegisterRequestDTO user) {
        UserEntity userEntity = repository.findByUser(user.name());
        if (userEntity != null) {
            throw new UserAlreadyExistsException();
        }
        return repository.save(new UserEntity(user.name(), user.user(), user.email(), passwordEncoder.encode(user.password()), UserRole.ADMIN));
    }

    public UserEntity updateUser(String id, UserEntity user) {
        return repository.save(user);
    }

    public void deleteUser(String userId) {
        repository.deleteById(userId);
    }
}
