package com.microservices.warehouse.applications.exceptions;

import com.microservices.user.application.responses.ResponseMessageType;

public class ExpiredTokenException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.EXPIRED_TOKEN.message();

    public ExpiredTokenException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public ExpiredTokenException(String input) {
        super(message, input);
    }
}
