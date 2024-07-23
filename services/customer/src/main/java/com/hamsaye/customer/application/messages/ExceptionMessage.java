package com.hamsaye.customer.application.messages;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionMessage (
        int code,
        String message,
        LocalDateTime timestamp,
        String cause
) {
}