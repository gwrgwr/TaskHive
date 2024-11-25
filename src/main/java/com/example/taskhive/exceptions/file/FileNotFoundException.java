package com.example.taskhive.exceptions.file;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException() {
        super("File Not Found");
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
