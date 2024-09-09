package com.hamsaye.chat.kafka.requests;

import lombok.Getter;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Getter
public enum MessageNotifyType {
    SEND_NEW_MESSAGE("new message is sent"),
    UPDATE_CHAT_MESSAGE("conversation and message is injected");

    private final String message;

    MessageNotifyType(String message) {
        this.message = message;
    }
}
