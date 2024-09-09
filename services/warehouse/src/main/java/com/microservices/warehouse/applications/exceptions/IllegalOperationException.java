package com.microservices.warehouse.applications.exceptions;

import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class IllegalOperationException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.ILLEGAL_REQUEST.message();

    public IllegalOperationException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public IllegalOperationException(String input) {
        super(message, input);
    }
}
