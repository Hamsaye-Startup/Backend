package com.hamsaye.customer.application.responses;

import lombok.Builder;

@Builder
public record ResponseFormat(
        Integer code,
        String message
) {
}
