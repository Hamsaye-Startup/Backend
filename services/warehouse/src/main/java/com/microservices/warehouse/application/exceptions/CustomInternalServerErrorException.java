package com.microservices.warehouse.application.exceptions;

public class CustomInternalServerErrorException extends CustomRuntimeException {

    private static final String key = "internal";

    public CustomInternalServerErrorException(String message) {
        super(message, key);
    }
}
