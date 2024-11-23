package com.example.taskhive.services;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }
}
