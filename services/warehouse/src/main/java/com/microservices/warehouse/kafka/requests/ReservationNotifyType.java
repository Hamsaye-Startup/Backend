package com.microservices.warehouse.kafka.requests;

import lombok.Getter;

@Getter
public enum ReservationNotifyType {
    NEW_RESERVE("new reservation is registered");

    private final String message;

    ReservationNotifyType(String message) {
        this.message = message;
    }
}
