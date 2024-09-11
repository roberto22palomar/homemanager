package com.example.homeManager.api.controllers.error_handler;



import com.example.homeManager.api.models.responses.error.BaseErrorResponse;
import com.example.homeManager.api.models.responses.error.ErrorResponse;
import com.example.homeManager.utils.exceptions.IdNotFoundException;
import com.example.homeManager.utils.exceptions.InvitacionFinalizadaException;
import com.example.homeManager.utils.exceptions.UserAlreadyAddedInCasa;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(UserAlreadyAddedInCasa.class)
    public BaseErrorResponse userAlreadyInCasa(UserAlreadyAddedInCasa exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(InvitacionFinalizadaException.class)
    public BaseErrorResponse handleInvitacionFinalizada(InvitacionFinalizadaException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
