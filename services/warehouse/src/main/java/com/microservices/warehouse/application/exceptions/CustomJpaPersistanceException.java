package com.microservices.warehouse.application.exceptions;

public class CustomJpaPersistanceException extends CustomRuntimeException {

    private static final String key = "cannot-persist-object";

    public CustomJpaPersistanceException(String message) {
        super(message, key);
    }
}
