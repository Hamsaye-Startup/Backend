package com.microservices.reservation.transactions.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class NotFoundTransactionException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.TRANSACTION_NOT_FOUND.message();

    public NotFoundTransactionException(String input) {
        super(message, input);
    }

    public NotFoundTransactionException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
