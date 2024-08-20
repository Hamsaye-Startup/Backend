package com.hamsaye.chat.users.exceptions;


import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.responses.ResponseMessageType;

public class PersistUserException extends CustomRuntimeException {

    private static final String message = ResponseMessageType.PERSIST_USER.message();

    public PersistUserException(String input) {
        super(message, input);
    }

    public PersistUserException(Throwable cause, String input) {
        super(message, cause, input);
    }
}
