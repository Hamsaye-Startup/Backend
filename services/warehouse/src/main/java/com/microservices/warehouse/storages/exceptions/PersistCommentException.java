package com.microservices.warehouse.storages.exceptions;

import com.microservices.warehouse.applications.exceptions.CustomRuntimeException;
import com.microservices.warehouse.applications.responses.ResponseMessageType;

public class PersistCommentException extends CustomRuntimeException {
    private static final String message = ResponseMessageType.PERSIST_COMMENT.message();

    public PersistCommentException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public PersistCommentException(String input) {
        super(message, input);
    }
}
