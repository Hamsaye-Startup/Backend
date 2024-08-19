package com.hamsaye.chat.users.requests;

import lombok.Getter;

@Getter
public enum UserNotifyType {
    NEW_USER("new user is added :: chat-service"),
    CONNECTED_USER("user is connected :: chat-service"),
    DISCONNECTED_USER("user is disconnected :: chat-service");

    private final String message;

    UserNotifyType(String message) {
        this.message = message;
    }
}
