package com.microservices.reservation.warehouse.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class AuthenticationCredentialNotFoundException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.AUTHENTICATION_CREDENTIAL_NOT_FOUND.message();

    public AuthenticationCredentialNotFoundException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public AuthenticationCredentialNotFoundException(String input) {
        super(message, input);
    }
}
