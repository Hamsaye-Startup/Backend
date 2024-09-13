package com.hamsaye.report.kafka.requests;

import lombok.Getter;

@Getter
public enum ViolationNotifyType {
    SEND_NEW_MESSAGE("new message is sent"),
    UPDATE_CHAT_MESSAGE("conversation and message is injected");

    private final String message;

    ViolationNotifyType(String message) {
        this.message = message;
    }
}
