package com.compassuol.sp.challenge.msusers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<StandardError> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Integrity violation");
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> ConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Integrity violation");
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Integrity violation");
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<StandardError> ParseException(ParseException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Please insert a valid data in format dd/mm/yyyy");
        error.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
