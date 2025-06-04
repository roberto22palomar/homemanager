package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción base para errores de API que transporta un HttpStatus.
 */
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
