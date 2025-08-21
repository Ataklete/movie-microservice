package com.real.interview.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ErrorResponse movieNotFoundHandler(MovieNotFoundException e) {
        return new ErrorResponse(LocalDate.now(), e.getMessage(), "record not in the database ");
    }
}
