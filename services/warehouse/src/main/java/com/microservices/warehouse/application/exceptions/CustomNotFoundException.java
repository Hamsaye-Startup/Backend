package com.microservices.warehouse.application.exceptions;

public class CustomNotFoundException extends CustomRuntimeException {

    private static final String key = "not-found-object";

    public CustomNotFoundException(String message) {
        super(message, key);
    }
}
