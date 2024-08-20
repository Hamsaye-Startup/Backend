package com.hamsaye.chat.messages.models;

import lombok.Builder;

@Builder
public record MessageDTO(
        String id,
        String content
) {
}
