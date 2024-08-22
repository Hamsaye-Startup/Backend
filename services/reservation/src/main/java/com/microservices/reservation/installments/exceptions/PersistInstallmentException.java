package com.microservices.reservation.installments.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class PersistInstallmentException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_INSTALLMENT.message();

    public PersistInstallmentException(String input) {
        super(message, input);
    }

    public PersistInstallmentException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
