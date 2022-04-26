package com.example.nc_spring_2022.controller;

import com.example.nc_spring_2022.dto.model.ErrorResponse;
import com.example.nc_spring_2022.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(Exception ex) {
        return ErrorResponse.badRequest(ex, 1);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(Exception ex) {
        return ErrorResponse.validationException(ex, 2);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorResponse handleEntityAlreadyExistsException(Exception ex) {
        return ErrorResponse.badRequest(ex, 3);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongCredentialsException.class)
    public ErrorResponse handleWrongCredentialsException(Exception ex) {
        return ErrorResponse.wrongCredentials(ex, 4);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ErrorResponse handleJwtAuthenticationException(Exception ex) {
        return ErrorResponse.badRequest(ex, 5);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleBadRequestException(Exception ex) {
        return ErrorResponse.validationException(ex, 6);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorizationException.class)
    public ErrorResponse handleAuthorizationException(Exception ex) {
        return ErrorResponse.validationException(ex, 7);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(Exception ex) {
        return ErrorResponse.unauthorized(ex, 8);
    }
}
