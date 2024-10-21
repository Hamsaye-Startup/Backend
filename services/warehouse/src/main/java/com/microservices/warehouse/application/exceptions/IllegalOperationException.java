package com.microservices.warehouse.application.exceptions;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class IllegalOperationException extends CustomRuntimeException {

    private static final String key = "illegal-request-happened";

    public IllegalOperationException(String message) {
        super(message, key);
    }
}
