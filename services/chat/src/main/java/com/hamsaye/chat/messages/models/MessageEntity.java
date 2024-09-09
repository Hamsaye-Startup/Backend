package com.hamsaye.chat.messages.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a message in a conversation.
 * <p>
 * This class is mapped to the "col_message" collection in MongoDB and contains the details
 * of a message, including its sender, associated conversation, content, and timestamp.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "col_message")
public class MessageEntity {

    /**
     * Unique identifier for the message.
     */
    @Id
    private String id;

    /**
     * UUID of the sender of the message.
     */
    @Field("sender_id")
    private UUID senderId;

    /**
     * UUID of the conversation to which this message belongs.
     * <p>
     * This field is unique within the collection to ensure that each conversation has only one message with this ID.
     * </p>
     */
    @Field("conversation_id")
    @Indexed(unique = true, name = "message_conversation_id_unique")
    private UUID conversationId;

    private String content;

    @Field("sender_at")
    private LocalDateTime sendAt;
}
