package com.microservices.warehouse.application.api.response;

import lombok.Builder;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ResponseFormat(
        Integer status,
        Integer code,
        String title,
        String description
) {
}
