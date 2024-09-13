package com.hamsaye.report.kafka.requests;

import lombok.Getter;

@Getter
public enum WarehouseViolationNotifyType {
    SEND_NEW_MESSAGE("new message is sent"),
    UPDATE_CHAT_MESSAGE("conversation and message is injected");

    private final String message;

    WarehouseViolationNotifyType(String message) {
        this.message = message;
    }
}
