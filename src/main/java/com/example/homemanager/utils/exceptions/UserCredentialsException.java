package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class UserCredentialsException extends ApiException {
    public UserCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos.");
    }
}
