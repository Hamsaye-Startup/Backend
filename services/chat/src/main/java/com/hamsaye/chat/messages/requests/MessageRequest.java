package com.hamsaye.chat.messages.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageRequest(
        UUID senderId,
        UUID conversationId,
        String content
) {
}
