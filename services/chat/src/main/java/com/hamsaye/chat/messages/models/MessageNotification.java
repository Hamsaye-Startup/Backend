package com.hamsaye.chat.messages.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageNotification(
        String id,
        UUID senderId,
        UUID conversationId,
        String content,
        String message,
        MessageNotifyType type
) {
}
