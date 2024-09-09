package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class InternalErrorException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.INTERNAL.message();

    public InternalErrorException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public InternalErrorException(String input) {
        super(message, input);
    }
}
