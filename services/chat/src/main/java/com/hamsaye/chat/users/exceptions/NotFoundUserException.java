package com.hamsaye.chat.users.exceptions;

import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundUserException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.EXPIRED_TOKEN.message();

    public NotFoundUserException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundUserException(String input) {
        super(message, input);
    }
}
