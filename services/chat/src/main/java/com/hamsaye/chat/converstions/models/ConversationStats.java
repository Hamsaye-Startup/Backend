package com.hamsaye.chat.converstions.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record ConversationStats(
        @Field("event_timing")
        ConversationEventTiming eventTiming,
        ConversationEventStats stats
) {
}
