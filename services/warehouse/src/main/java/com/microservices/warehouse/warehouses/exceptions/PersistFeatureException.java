package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class PersistFeatureException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_FEATURE.message();

    public PersistFeatureException(String input) {
        super(message, input);
    }

    public PersistFeatureException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
