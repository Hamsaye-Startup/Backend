package com.hamsaye.customer.customers.exceptions;

import com.hamsaye.customer.application.exceptions.CustomRuntimeException;
import com.hamsaye.customer.application.responses.ResponseMessageType;

public class PersistCustomerException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_CUSTOMER.message();

    public PersistCustomerException(String input) {
        super(message, input);
    }

    public PersistCustomerException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
