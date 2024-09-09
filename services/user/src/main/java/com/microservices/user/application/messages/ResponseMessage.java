package com.microservices.user.application.messages;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ResponseMessage (
        int code,
        String message,
        LocalDateTime timestamp,
        Object result
) {
}
