package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundReservationException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.RESERVATION_NOT_FOUND.message();

    public NotFoundReservationException(String input) {
        super(message, input);
    }

    public NotFoundReservationException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
