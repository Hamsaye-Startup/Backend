package com.hamsaye.chat.applications.responses;

import lombok.Builder;

@Builder
public record ResponseFormat(
        Integer code,
        String message
) {
}
