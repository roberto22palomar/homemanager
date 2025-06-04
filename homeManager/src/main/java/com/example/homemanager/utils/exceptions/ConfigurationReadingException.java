package com.example.homemanager.utils.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando ocurre un problema al leer o cargar la configuración.
 */
public class ConfigurationReadingException extends ApiException {

    public ConfigurationReadingException(String configurationName) {
        super(HttpStatus.BAD_REQUEST, "The configuration: "+configurationName+ "couldn't be read");
    }
}
