package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class StorageIsNotRemovableException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.NOT_REMOVABLE_STORAGE.message();

    public StorageIsNotRemovableException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public StorageIsNotRemovableException(String input) {
        super(message, input);
    }
}
