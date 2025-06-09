package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyMemberExepction extends ApiException {

    public UserAlreadyMemberExepction(String message) {
        super(HttpStatus.CONFLICT,message);
    }

}
