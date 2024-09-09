package com.microservices.user.roles.exceptions;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class PersistRoleException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_ROLE.message();

    public PersistRoleException(String input) {
        super(message, input);
    }

    public PersistRoleException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
