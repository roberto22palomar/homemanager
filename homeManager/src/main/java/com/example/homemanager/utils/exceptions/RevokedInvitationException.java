package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class RevokedInvitationException extends ApiException {


    public RevokedInvitationException(String idInvitation) {
        super(HttpStatus.FORBIDDEN, "The invitation: " + idInvitation + " is revoked.");
    }

}

