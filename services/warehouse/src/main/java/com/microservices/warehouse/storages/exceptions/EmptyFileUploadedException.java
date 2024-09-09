package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class EmptyFileUploadedException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.EMPTY_FILE_UPLOADED.message();

    public EmptyFileUploadedException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public EmptyFileUploadedException(String input) {
        super(message, input);
    }
}
