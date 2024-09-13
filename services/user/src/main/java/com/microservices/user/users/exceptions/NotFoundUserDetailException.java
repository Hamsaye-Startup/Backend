package com.microservices.user.users.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundUserDetailException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.USER_DETAIL_NOT_FOUND.message();

    public NotFoundUserDetailException(String input) {
        super(message, input);
    }

    public NotFoundUserDetailException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
