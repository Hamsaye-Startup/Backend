package com.microservices.warehouse.application.exceptions;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class AuthenticationCredentialNotFoundException extends CustomRuntimeException {

    private static final String key = "not-exist-authentication-credential";

    public AuthenticationCredentialNotFoundException(String message) {
        super(message, key);
    }
}
