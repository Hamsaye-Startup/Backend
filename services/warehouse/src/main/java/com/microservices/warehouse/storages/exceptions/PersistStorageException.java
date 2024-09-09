package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistStorageException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_STORAGE.message();

    public PersistStorageException(String input) {
        super(message, input);
    }

    public PersistStorageException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
