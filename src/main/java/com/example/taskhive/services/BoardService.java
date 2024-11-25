package com.example.taskhive.services;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.board.CreateRequestBoardDTO;
import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.exceptions.board.BoardAlreadyExistsException;
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
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository repository, UserRepository userRepository, BoardRepository boardRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
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

    public Board saveBoard(CreateRequestBoardDTO data) {
        Board boardExists = boardRepository.findByName(data.name()).orElseThrow(BoardNotFoundException::new);
        UserEntity user = userRepository.findById(data.createdBy()).orElseThrow();
        Board board = new Board(data.name(), data.description(), user);
        return repository.save(board);
    }

    public String generateAccessCode(String boardId) {
        SecureRandom secureRandom = new SecureRandom();
        int token = 10000000 + secureRandom.nextInt(90000000);
        Board board = repository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        board.setAccessCode(String.valueOf(token).toUpperCase());
        repository.save(board);
        return String.valueOf(token).toUpperCase();
    }

    public void addCollaborator(String accessCode, String userId) {
        Board board = repository.findByAccessCode(accessCode).orElseThrow(BoardNotFoundException::new);
        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        board.getCollaborators().add(user);
        repository.save(board);
    }
    public void deleteBoard(String boardId) {
        Board board = repository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        repository.delete(board);
    }

    public void deleteUserFromBoard(String boardId, String userId) {
        Board board = repository.findById(boardId).orElseThrow(UserNotFoundException::new);
        UserEntity user = userRepository.findById(userId).orElseThrow(BoardNotFoundException::new);
        board.getCollaborators().remove(user);
        repository.save(board);
    }
}
