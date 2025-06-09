package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT, "Username already in use: " + username);
    }
}
