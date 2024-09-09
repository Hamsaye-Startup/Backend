package com.microservices.user.customers.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistCustomerException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_CUSTOMER.message();

    public PersistCustomerException(String input) {
        super(message, input);
    }

    public PersistCustomerException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
