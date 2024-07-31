package com.microservices.reservation.applications.messages;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResponseMessage (
        int code,
        String message,
        LocalDateTime timestamp,
        Object result
) {
}
