package com.microservices.warehouse.application.api.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ExceptionMessage (
        Integer status,
        Integer code,
        String title,
        Object message,
        LocalDateTime timestamp,
        String path,
        String requestId
) {
}