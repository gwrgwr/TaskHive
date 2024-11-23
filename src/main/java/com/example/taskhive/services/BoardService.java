package com.example.taskhive.services;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository repository;

    public List<Board> getAllBoardsFromUser(String userId) {
        return repository.findAllByCreatedBy_Id(userId);
    }

    public List<Board> getAllColoborationsFromUser(String userId) {
        return repository.findByCollaborators_Id(userId);
    }
}
