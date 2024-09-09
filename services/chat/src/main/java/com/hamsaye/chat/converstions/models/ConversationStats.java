package com.hamsaye.chat.converstions.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents a record that holds statistical information for a conversation.
 * This record includes details about event timing and associated statistics.
 *
 * <p>The {@code eventTiming} field is annotated with {@code @Field} to map it to the
 * corresponding field in a MongoDB document.
 *
 * @param eventTiming The timing information related to the conversation event.
 * @param stats The statistical data associated with the conversation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ConversationStats(
        @Field("event_timing")
        ConversationEventTiming eventTiming,
        ConversationEventStats stats
) {
}
