package com.microservices.warehouse.geos.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistAddressException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_ADDRESS.message();

    public PersistAddressException(String input) {
        super(message, input);
    }

    public PersistAddressException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
