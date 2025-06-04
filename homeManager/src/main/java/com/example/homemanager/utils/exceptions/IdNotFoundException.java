package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends ApiException {
    public IdNotFoundException(String entityName, String id) {
        super(HttpStatus.NOT_FOUND, entityName + " not found with id: " + id);
    }
}
