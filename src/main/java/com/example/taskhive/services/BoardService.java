package com.example.taskhive.services;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.board.CreateBoardRequestDTO;
import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.exceptions.board.BoardNotFoundException;
import com.example.taskhive.exceptions.user.UserNotFoundException;
import com.example.taskhive.repositories.BoardRepository;
import com.example.taskhive.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository repository;
    private final UserService userService;

    public BoardService(BoardRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Board> getAllBoardsFromUser(String userId) {
        return repository.findAllByCreatedBy_Id(userId);
    }

    public List<Board> getAllCollaborationFromUser(String userId) {
        return repository.findByCollaborators_Id(userId);
    }

    public List<Board> getAllBoards() {
        return repository.findAll();
    }

    public Board getBoardById(String boardId) {
        return repository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }

    public List<ListEntity> getListsFromBoard(String boardId) {
        Board board = getBoardById(boardId);
        return board.getLists();
    }

    public String generateAccessCode(String boardId) {
        SecureRandom secureRandom = new SecureRandom();
        int token = 10000000 + secureRandom.nextInt(90000000);
        Board board = getBoardById(boardId);

        board.setAccessCode(String.valueOf(token).toUpperCase());
        repository.save(board);
        return String.valueOf(token).toUpperCase();
    }

    public Board saveBoard(CreateBoardRequestDTO data) {
        repository.findByName(data.name()).orElseThrow(BoardNotFoundException::new);
        UserEntity user = userService.getUserById(data.createdBy());
        Board board = new Board(data.name(), data.description(), user);
        return repository.save(board);
    }

    public void addCollaborator(String accessCode, String userId) {
        Board board = repository.findByAccessCode(accessCode).orElseThrow(BoardNotFoundException::new);
        UserEntity user = userService.getUserById(userId);
        board.getCollaborators().add(user);
        repository.save(board);
    }

    public void deleteBoard(String boardId) {
        Board board = getBoardById(boardId);
        repository.delete(board);
    }

    public void deleteUserFromBoard(String boardId, String userId) {
        Board board = getBoardById(boardId);
        UserEntity user = userService.getUserById(userId);
        board.getCollaborators().remove(user);
        repository.save(board);
    }
}
