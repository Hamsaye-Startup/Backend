package com.microservices.user.kafka.requests;

import lombok.Getter;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Getter
public enum UserNotifyType {
    NEW_USER("new user is registered"),
    UPDATE_USER("general user information is updated"),
    DELETE_USER("user is deleted");

    private final String message;

    UserNotifyType(String message) {
        this.message = message;
    }
}
