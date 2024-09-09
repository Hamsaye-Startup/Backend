package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundBookmarkException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.BOOKMARK_NOT_FOUND.message();

    public NotFoundBookmarkException(String input) {
        super(message, input);
    }

    public NotFoundBookmarkException(Throwable cause, String input) {
        super(message, cause, input);
    }

}
