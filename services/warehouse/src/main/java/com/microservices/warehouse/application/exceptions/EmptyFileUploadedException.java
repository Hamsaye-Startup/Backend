package com.microservices.warehouse.application.exceptions;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class EmptyFileUploadedException extends CustomRuntimeException {

    private static final String key = "empty-file-uploaded";

    public EmptyFileUploadedException(String message) {
        super(message, key);
    }
}
