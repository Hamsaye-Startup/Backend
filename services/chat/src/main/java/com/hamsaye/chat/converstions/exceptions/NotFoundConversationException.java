package com.hamsaye.chat.converstions.exceptions;

import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class NotFoundConversationException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.CHAT_CONVERSATION_NOT_FOUND.message();

    public NotFoundConversationException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public NotFoundConversationException(String input) {
        super(message, input);
    }
}
