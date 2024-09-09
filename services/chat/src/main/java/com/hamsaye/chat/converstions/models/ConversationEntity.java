package com.hamsaye.chat.converstions.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * Represents a conversation entity stored in MongoDB.
 * This class contains information about the conversation, including its unique identifier,
 * initiation details, involved users, and conversation statistics.
 *
 * <p>It uses Lombok annotations for boilerplate code generation and Spring Data MongoDB
 * annotations for mapping the class to a MongoDB document.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "col_conversation")
public class ConversationEntity {

    @Id
    private UUID uid;

    /**
     * The enumeration indicating how the conversation was initiated.
     */
    @Field("initiated_by")
    private InitiationByEnum initiatedBy;

    /**
     * The collection of user references associated with the conversation.
     */
    private UserReferenceCollection users;

    /**
     * The statistical information related to the conversation.
     */
    @Field("conversation_stats")
    private ConversationStats conversationStats;
}
