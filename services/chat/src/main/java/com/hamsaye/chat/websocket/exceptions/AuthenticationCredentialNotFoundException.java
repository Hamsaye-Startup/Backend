package com.hamsaye.chat.websocket.exceptions;

import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

/*
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
