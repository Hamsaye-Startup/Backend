package com.hamsaye.chat.messages.models;

import lombok.Getter;

@Getter
public enum MessageNotifyType {
    SEND_NEW_MESSAGE("new message is sent"),
    UPDATE_CHAT_MESSAGE("conversation and message is injected");

    private final String message;

    MessageNotifyType(String message) {
        this.message = message;
    }
}
