package com.hamsaye.chat.applications.messages;

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