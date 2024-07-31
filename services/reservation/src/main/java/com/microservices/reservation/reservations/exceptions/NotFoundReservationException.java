package com.microservices.reservation.reservations.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

public class NotFoundReservationException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.RESERVATION_NOT_FOUND.message();

    public NotFoundReservationException(String input) {
        super(message, input);
    }

    public NotFoundReservationException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
