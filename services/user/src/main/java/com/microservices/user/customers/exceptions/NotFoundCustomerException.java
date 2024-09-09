package com.microservices.user.customers.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundCustomerException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.CUSTOMER_NOT_FOUND.message();

    public NotFoundCustomerException(String input) {
        super(message, input);
    }

    public NotFoundCustomerException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
