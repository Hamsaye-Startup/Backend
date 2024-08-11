package com.microservices.reservation.products.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class NotFoundProductTypeException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PRODUCT_TYPE_NOT_FOUND.message();

    public NotFoundProductTypeException(String input) {
        super(message, input);
    }

    public NotFoundProductTypeException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
