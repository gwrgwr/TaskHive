package com.example.taskhive.exceptions.task;

public class TaskAlreadyExistsException extends RuntimeException{
    public TaskAlreadyExistsException(String message) {
        super(message);
    }

    public TaskAlreadyExistsException() {
        super("Task Already Exists");
    }
}
