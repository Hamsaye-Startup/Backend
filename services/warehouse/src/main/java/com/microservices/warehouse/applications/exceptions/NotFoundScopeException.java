package com.microservices.warehouse.applications.exceptions;

import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundScopeException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.SCOPE_NOT_FOUND.message();

    public NotFoundScopeException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundScopeException(String input) {
        super(message, input);
    }
}
