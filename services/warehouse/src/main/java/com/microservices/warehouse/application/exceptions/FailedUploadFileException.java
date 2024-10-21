package com.microservices.warehouse.application.exceptions;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class FailedUploadFileException extends CustomRuntimeException {

    private static final String key = "failed-upload-file";

    public FailedUploadFileException(String message) {
        super(message, key);
    }
}
