package com.microservices.warehouse.application.api.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ResponseMessage (
        Integer status,
        Integer code,
        String title,
        String message,
        LocalDateTime timestamp,
        String requestId,
        String path,
        Object result
) {
}
