package com.hamsaye.customer.application.exceptions;

import com.hamsaye.customer.application.responses.ResponseMessageType;

public class NotFoundScopeException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.SCOPE_NOT_FOUND.message();

    public NotFoundScopeException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundScopeException(String input) {
        super(message, input);
    }
}
