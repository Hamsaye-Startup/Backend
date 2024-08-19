package com.hamsaye.chat.converstions.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record NewConversationRequest(
        UUID senderId,
        UUID recipientId,
        String content
) {
}
