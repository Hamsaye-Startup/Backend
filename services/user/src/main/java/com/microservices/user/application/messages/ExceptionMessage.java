package com.microservices.user.application.messages;

import lombok.Builder;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
public record ExceptionMessage (
        int code,
        String message,
        LocalDateTime timestamp,
        String cause
) {
}