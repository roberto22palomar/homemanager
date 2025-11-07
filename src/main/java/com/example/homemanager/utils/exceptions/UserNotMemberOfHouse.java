package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotMemberOfHouse extends ApiException {


    public UserNotMemberOfHouse(String user, String house) {
        super(HttpStatus.FORBIDDEN, "The user" + user + "isn't member of the house: " + house);
    }

}

