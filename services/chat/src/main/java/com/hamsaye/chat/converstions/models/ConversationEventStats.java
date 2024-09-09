package com.hamsaye.chat.converstions.models;

import com.hamsaye.chat.messages.models.MessageDTO;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents statistics for a conversation event, including details of the last message sent.
 * <p>
 * This record encapsulates the statistical information related to a conversation event,
 * specifically focusing on the last message sent within the conversation.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ConversationEventStats(

        /**
         * The last message sent in the conversation.
         * <p>
         * This field holds details about the most recent message in the conversation,
         * represented as a {@link MessageDTO} object.
         * </p>
         */
        @Field("last_message")
        MessageDTO lastMessageDto

) {
}
