package com.example.taskhive.exceptions.board;

public class BoardAlreadyExistsException extends RuntimeException {
    public BoardAlreadyExistsException(String message) {
        super(message);
    }

    public BoardAlreadyExistsException() {
        super("Board already Exists");
    }
}
