package com.hamsaye.chat.converstions.models;

import com.hamsaye.chat.messages.models.MessageDTO;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record ConversationEventStats(
        @Field("last_message")
        MessageDTO lastMessageDto
) {
}
