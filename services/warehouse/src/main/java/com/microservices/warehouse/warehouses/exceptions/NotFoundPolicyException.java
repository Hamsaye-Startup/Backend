package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundPolicyException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.FEATURE_NOT_FOUND.message();

    public NotFoundPolicyException(String input) {
        super(message, input);
    }

    public NotFoundPolicyException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
