package com.example.taskhive.exceptions.board;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String message) {
        super(message);
    }

    public BoardNotFoundException() {
        super("Board does not exist");
    }
}
