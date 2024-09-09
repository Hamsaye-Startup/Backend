package com.hamsaye.chat.messages.requests;

import lombok.Builder;

import java.util.UUID;

/**
 * Request object for creating or sending a message.
 * <p>
 * This record contains the necessary information to create or send a message within a conversation,
 * including the sender's ID, the conversation ID, and the message content.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record MessageRequest(
        UUID senderId,
        UUID conversationId,
        String content
) {
}
