package com.hamsaye.chat.converstions.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Represents a record that holds timing information for a conversation event.
 * This record stores the timestamp of the last message in the conversation.
 * It uses Lombok's {@code @Builder} annotation to facilitate the creation of immutable instances.
 *
 * <p>The {@code lastMessageTime} field is annotated with {@code @Field} to map it to the
 * corresponding field in a MongoDB document.
 *
 * @param lastMessageTime The timestamp of the last message in the conversation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ConversationEventTiming(
        @Field("last_message_time")
        LocalDateTime lastMessageTime
) {
}
