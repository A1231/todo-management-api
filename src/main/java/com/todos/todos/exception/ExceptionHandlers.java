package com.todos.todos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleResponses(ResponseStatusException exc){
        return buildResponseEntity(exc, HttpStatus.valueOf(exc.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleResponses(Exception exc){
        return buildResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<ExceptionResponses> buildResponseEntity(Exception exc, HttpStatus status){
        ExceptionResponses error = new ExceptionResponses(status.value(), exc.getMessage(), System.currentTimeMillis() );

        return new ResponseEntity<>(error, status);
    }
}
