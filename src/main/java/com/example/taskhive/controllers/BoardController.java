package com.example.taskhive.controllers;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.services.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@Tag(name = "Board", description = "Endpoint for managing boards from user")
public class BoardController {

    @Autowired
    private BoardService service;

    @GetMapping(value = "createdBoards/{userId}")
    public List<Board> getCreatedBoardsBy(@PathVariable String userId) {
        return service.getAllBoardsFromUser(userId);
    }

    @GetMapping(value = "collaboratedBoards/{userId}")
    public List<Board> getCollaboratedBoardsBy(@PathVariable String userId) {
        return service.getAllColoborationsFromUser(userId);
    }
}
