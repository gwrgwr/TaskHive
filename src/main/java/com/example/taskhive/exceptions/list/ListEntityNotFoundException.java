package com.example.taskhive.exceptions.list;

public class ListEntityNotFoundException extends RuntimeException{
    public ListEntityNotFoundException(String message) {
        super(message);
    }

    public ListEntityNotFoundException() {
        super("List Not Found");
    }
}
