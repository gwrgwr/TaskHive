package com.example.taskhive.repositories;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserDetails findUsersByUser(String user);

    UserDetails findUsersByEmail(String email);
}
