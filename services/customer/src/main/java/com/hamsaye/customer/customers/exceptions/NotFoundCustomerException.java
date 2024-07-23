package com.hamsaye.customer.customers.exceptions;

import com.hamsaye.customer.application.exceptions.CustomRuntimeException;
import com.hamsaye.customer.application.responses.ResponseMessageType;

public class NotFoundCustomerException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.CUSTOMER_NOT_FOUND.message();

    public NotFoundCustomerException(String input) {
        super(message, input);
    }

    public NotFoundCustomerException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
