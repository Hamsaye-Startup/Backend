package com.microservices.warehouse.applications.messages;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ExceptionMessage (
        int code,
        String message,
        LocalDateTime timestamp,
        String cause
) {
}