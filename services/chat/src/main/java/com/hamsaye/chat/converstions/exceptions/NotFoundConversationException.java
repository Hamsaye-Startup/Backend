package com.hamsaye.chat.converstions.exceptions;

import java.util.UUID;

public class NotFoundConversationException extends RuntimeException {
    public NotFoundConversationException(UUID uid) {
    }
}
