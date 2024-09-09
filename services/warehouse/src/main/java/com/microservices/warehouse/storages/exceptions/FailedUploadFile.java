package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class FailedUploadFile extends CustomRuntimeException {
    private static final String message = ResponseMessageType.FAILED_UPLOAD_FILE.message();

    public FailedUploadFile(Throwable cause, String input) {
        super(message, cause, input);
    }

    public FailedUploadFile(String input) {
        super(message, input);
    }
}
