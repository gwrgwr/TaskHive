package com.example.taskhive.exceptions.global;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException() {
        super("Internal Server Error");
    }
}
