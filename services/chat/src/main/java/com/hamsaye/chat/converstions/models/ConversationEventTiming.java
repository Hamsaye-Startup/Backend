package com.hamsaye.chat.converstions.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Builder
public record ConversationEventTiming(
        @Field("last_message_time")
        LocalDateTime lastMessageTime
) {
}
