package com.example.taskhive.controllers;

import com.example.taskhive.annotations.board.*;
import com.example.taskhive.annotations.list.GetListAnnotation;
import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.board.CreateBoardRequestDTO;
import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.services.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@Tag(name = "Board", description = "Endpoint for managing boards from user")
public class BoardController {

    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping
    @GetBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Board> getAllBoards() {
        return service.getAllBoards();
    }

    @GetMapping(value = "createdBoards/{userId}")
    @GetBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public List<Board> getCreatedBoardsBy(@PathVariable String userId) {
        return service.getAllBoardsFromUser(userId);
    }

    @GetMapping(value = "collaboratedBoards/{userId}")
    @GetBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Board> getCollaboratedBoardsBy(@PathVariable String userId) {
        return service.getAllCollaborationFromUser(userId);
    }

    @GetMapping(value = "board/{boardId}")
    @GetBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Board getBoardById(@PathVariable String boardId) {
        return service.getBoardById(boardId);
    }

    @GetMapping(value = "lists/{boardId}")
    @GetListAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<ListEntity> getListsFromBoard(@PathVariable String boardId) {
        return service.getListsFromBoard(boardId);
    }

    @PostMapping
    @CreateBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public Board saveBoard(@RequestBody CreateBoardRequestDTO data) {
        return service.saveBoard(data);
    }

    @PutMapping(value = "generateAccessCode/{boardId}")
    @GenerateAccessTokenBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public String generateAccessCode(@PathVariable String boardId) {
        return service.generateAccessCode(boardId);
    }

    @PutMapping(value = "addCollaborator/{accessCode}/{userId}")
    @AddCollaboratorBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void addCollaborator(@PathVariable String accessCode, @PathVariable String userId) {
        service.addCollaborator(accessCode, userId);
    }

    @DeleteMapping(value = "board/{boardId}")
    @DeleteBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public void deleteBoard(@PathVariable String boardId) {
        service.deleteBoard(boardId);
    }

    @DeleteMapping(value = "deleteUserFromBoard/{boardId}/{userId}")
    @DeleteCollaboratorBoardAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public void deleteUserFromBoard(@PathVariable String boardId, @PathVariable String userId) {
        service.deleteUserFromBoard(boardId, userId);
    }

}
