package com.microservices.reservation.installments.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class NotFoundInstallmentException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.INSTALLMENT_NOT_FOUND.message();

    public NotFoundInstallmentException(String input) {
        super(message, input);
    }

    public NotFoundInstallmentException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
