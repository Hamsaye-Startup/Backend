package com.microservices.user.application.responses;

import lombok.Builder;

@Builder
public record ResponseFormat(
        Integer code,
        String message
) {
}
