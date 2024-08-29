package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundPolicyException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.POLICY_NOT_FOUND.message();

    public NotFoundPolicyException(String input) {
        super(message, input);
    }

    public NotFoundPolicyException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
