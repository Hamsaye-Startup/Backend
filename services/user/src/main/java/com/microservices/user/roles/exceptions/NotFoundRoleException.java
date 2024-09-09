package com.microservices.user.roles.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundRoleException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.ROLE_NOT_FOUND.message();

    public NotFoundRoleException(String input) {
        super(message, input);
    }

    public NotFoundRoleException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
