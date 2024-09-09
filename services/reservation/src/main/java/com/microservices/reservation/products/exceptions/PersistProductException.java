package com.microservices.reservation.products.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistProductException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_PRODUCT.message();

    public PersistProductException(String input) {
        super(message, input);
    }

    public PersistProductException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
