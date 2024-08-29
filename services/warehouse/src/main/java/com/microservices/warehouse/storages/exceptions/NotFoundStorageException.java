package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundStorageException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.STORAGE_NOT_FOUND.message();

    public NotFoundStorageException(String input) {
        super(message, input);
    }

    public NotFoundStorageException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
