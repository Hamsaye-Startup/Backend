package com.microservices.user.users.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundUserException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.USER_NOT_FOUND.message();

    public NotFoundUserException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundUserException(String input) {
        super(message, input);
    }
}
