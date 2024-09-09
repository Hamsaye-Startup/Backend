package com.microservices.reservation.transactions.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistTransactionException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_TRANSACTION.message();

    public PersistTransactionException(String input) {
        super(message, input);
    }

    public PersistTransactionException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
