package com.hamsaye.chat.messages.exceptions;

import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

public class ConversationAlreadyExistsException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.CHAT_CONVERSATION_ALREADY_EXISTS.message();

    public ConversationAlreadyExistsException(Throwable cause, String input) {
        super(message, cause, input);
    }

    public ConversationAlreadyExistsException(String input) {
        super(message, input);
    }
}
