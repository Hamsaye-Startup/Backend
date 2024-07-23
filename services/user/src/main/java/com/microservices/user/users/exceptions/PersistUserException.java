package com.microservices.user.users.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

public class PersistUserException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_USER.message();

    public PersistUserException(String input) {
        super(message, input);
    }

    public PersistUserException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
