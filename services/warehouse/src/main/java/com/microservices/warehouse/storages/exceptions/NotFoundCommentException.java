package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class NotFoundCommentException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.COMMENT_NOT_FOUND.message();

    public NotFoundCommentException(String input) {
        super(message, input);
    }

    public NotFoundCommentException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
