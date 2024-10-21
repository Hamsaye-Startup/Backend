package com.microservices.warehouse.application.exceptions;

public class CustomNotRemovableObjectException extends CustomRuntimeException {

    private static final String key = "not-removable-object";

    public CustomNotRemovableObjectException(String message) {
        super(message, key);
    }
}
