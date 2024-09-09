package com.microservices.user.application.exceptions;

import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class ExpiredTokenException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.EXPIRED_TOKEN.message();

    public ExpiredTokenException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public ExpiredTokenException(String input) {
        super(message, input);
    }
}
