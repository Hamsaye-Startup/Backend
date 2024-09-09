package com.microservices.user.application.responses;

import lombok.Builder;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ResponseFormat(
        Integer code,
        String message
) {
}
