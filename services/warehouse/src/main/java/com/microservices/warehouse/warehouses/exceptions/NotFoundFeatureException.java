package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundFeatureException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.FEATURE_NOT_FOUND.message();

    public NotFoundFeatureException(String input) {
        super(message, input);
    }

    public NotFoundFeatureException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
