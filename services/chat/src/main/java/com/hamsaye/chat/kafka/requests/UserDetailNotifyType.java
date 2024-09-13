package com.hamsaye.chat.kafka.requests;

import lombok.Getter;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Getter
public enum UserDetailNotifyType {

    NEW_USER("new customer information is saved"),
    UPDATE_USER_INFO("customer information is updated"),
    DELETE_USER_INFO("customer information is deleted");

    private final String message;

    UserDetailNotifyType(String message) {
        this.message = message;
    }
}
