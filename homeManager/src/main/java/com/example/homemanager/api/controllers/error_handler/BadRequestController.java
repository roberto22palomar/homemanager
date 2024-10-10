package com.example.homemanager.api.controllers.error_handler;


import com.example.homemanager.api.models.responses.error.BaseErrorResponse;
import com.example.homemanager.api.models.responses.error.ErrorResponse;
import com.example.homemanager.utils.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(UserAlreadyExists.class)
    public BaseErrorResponse userAlreadyExists(UserAlreadyExists exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(UserAlreadyMember.class)
    public BaseErrorResponse userAlreadyMember(UserAlreadyMember exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }



    @ExceptionHandler(RevokedInvitationException.class)
    public BaseErrorResponse revokedInvitationException(RevokedInvitationException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(UserNotMemberOfHouse.class)
    public BaseErrorResponse userNotMemberOfHouse(UserNotMemberOfHouse exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(UserCredentialsException.class)
    public BaseErrorResponse userCredentialsException(UserCredentialsException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
