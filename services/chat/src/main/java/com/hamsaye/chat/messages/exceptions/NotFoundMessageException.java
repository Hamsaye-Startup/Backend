package com.hamsaye.chat.messages.exceptions;

import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundMessageException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.CHAT_MESSAGE_NOT_FOUND.message();

    public NotFoundMessageException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundMessageException(String input) {
        super(message, input);
    }
}
