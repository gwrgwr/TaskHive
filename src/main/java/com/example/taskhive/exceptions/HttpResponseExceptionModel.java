package com.example.taskhive.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public record HttpResponseExceptionModel(String path, HttpStatus status, String error, String message, LocalDateTime timestamp) {
    public HttpResponseExceptionModel(Exception exception, HttpServletRequest request, HttpStatus status, String error) {
        this(request.getRequestURI(), status, error, exception.getMessage(), LocalDateTime.now());
    }
}
