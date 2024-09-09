package com.microservices.reservation.warehouse.exceptions;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistReservationException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_RESERVATION.message();

    public PersistReservationException(String input) {
        super(message, input);
    }

    public PersistReservationException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
