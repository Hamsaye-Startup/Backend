package com.microservices.warehouse.warehouses.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundReviewException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.REVIEW_NOT_FOUND.message();

    public NotFoundReviewException(String input) {
        super(message, input);
    }

    public NotFoundReviewException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
