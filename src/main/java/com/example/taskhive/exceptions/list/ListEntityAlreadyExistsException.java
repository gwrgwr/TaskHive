package com.example.taskhive.exceptions.list;

public class ListEntityAlreadyExistsException extends RuntimeException{
    public ListEntityAlreadyExistsException(String message) {
        super(message);
    }

    public ListEntityAlreadyExistsException() {
        super("List Already Exists");
    }
}
