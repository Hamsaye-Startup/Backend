package com.hamsaye.chat.messages.models;

import lombok.Builder;

@Builder
public record MessageDTO(
        Long id,
        String content
) {
}
