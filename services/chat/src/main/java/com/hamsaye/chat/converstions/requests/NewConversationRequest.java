package com.hamsaye.chat.converstions.requests;

import lombok.Builder;

import java.util.UUID;

/**
 * Represents a request to start a new conversation.
 * This record contains the necessary information to create a new conversation, including
 * the sender's ID, the recipient's ID, and the content of the conversation.
 *
 * <p>It uses Lombok's {@code @Builder} annotation to facilitate the creation of immutable instances.
 *
 * @param senderId The unique identifier of the sender initiating the conversation.
 * @param recipientId The unique identifier of the recipient of the conversation.
 * @param content The content or message to be included in the new conversation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record NewConversationRequest(
        UUID senderId,
        UUID recipientId,
        String content
) {
}
