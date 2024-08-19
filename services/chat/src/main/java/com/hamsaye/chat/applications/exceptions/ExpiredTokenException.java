package com.hamsaye.chat.applications.exceptions;

import com.hamsaye.chat.applications.responses.ResponseMessageType;

public class ExpiredTokenException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.EXPIRED_TOKEN.message();

    public ExpiredTokenException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public ExpiredTokenException(String input) {
        super(message, input);
    }
}
