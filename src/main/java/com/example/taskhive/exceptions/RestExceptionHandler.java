package com.example.taskhive.exceptions;

import com.example.taskhive.exceptions.board.BoardNotFoundException;
import com.example.taskhive.exceptions.global.InternalServerErrorException;
import com.example.taskhive.exceptions.user.UserAlreadyExistsException;
import com.example.taskhive.exceptions.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<HttpResponseExceptionModel> userAlreadyExists(UserAlreadyExistsException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponseExceptionModel(exception, request, HttpStatus.BAD_REQUEST, "User already exists"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<HttpResponseExceptionModel> userNotFound(UserNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpResponseExceptionModel(exception, request, HttpStatus.NOT_FOUND, "User not found"));
    }

    @ExceptionHandler(BoardNotFoundException.class)
    private ResponseEntity<HttpResponseExceptionModel> boardNotFound(BoardNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpResponseExceptionModel(exception, request, HttpStatus.NOT_FOUND, "Board not found"));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    private ResponseEntity<HttpResponseExceptionModel> accessDenied(AuthorizationDeniedException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new HttpResponseExceptionModel(exception, request, HttpStatus.FORBIDDEN, "Access denied"));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    private ResponseEntity<HttpResponseExceptionModel> internalServerError(InternalServerErrorException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpResponseExceptionModel(exception, request, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
    }
}