package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundFeatureException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.FEATURE_NOT_FOUND.message();

    public NotFoundFeatureException(String input) {
        super(message, input);
    }

    public NotFoundFeatureException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
