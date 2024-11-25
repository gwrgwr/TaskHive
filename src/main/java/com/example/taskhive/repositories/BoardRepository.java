package com.example.taskhive.repositories;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, String> {
    List<Board> findAllByCreatedBy_Id(String createdById);

    List<Board> findByCollaborators_Id(String collaboratorId);

    Optional<Board> findByAccessCode(String accessCode);

    Optional<Board> findByName(String name);
}
