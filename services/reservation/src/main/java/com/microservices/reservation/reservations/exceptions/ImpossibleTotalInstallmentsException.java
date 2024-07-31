package com.microservices.reservation.reservations.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class ImpossibleTotalInstallmentsException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.TOTAL_INSTALLMENT.message();

    public ImpossibleTotalInstallmentsException(String input) {
        super(message, input);
    }

    public ImpossibleTotalInstallmentsException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
