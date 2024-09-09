package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistBookmarkException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_BOOKMARK.message();

    public PersistBookmarkException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public PersistBookmarkException(String input) {
        super(message, input);
    }
}
