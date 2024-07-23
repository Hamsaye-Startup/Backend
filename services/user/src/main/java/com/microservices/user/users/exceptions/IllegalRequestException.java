package com.microservices.user.users.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

public class IllegalRequestException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.ILLEGAL_REQUEST.message();

    public IllegalRequestException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public IllegalRequestException(String input) {
        super(message, input);
    }

    public IllegalRequestException(Throwable cause, String input, String guilty) {
        super(message  + " by " + guilty, cause, input);
    }

    public IllegalRequestException(String input, String guilty) {
        super(message + " by " + guilty, input);
    }
}
