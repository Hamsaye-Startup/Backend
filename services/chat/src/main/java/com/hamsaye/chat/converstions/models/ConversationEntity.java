package com.hamsaye.chat.converstions.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "col_conversation")
public class ConversationEntity {

    @Id
    private UUID uid;

    @Field("initiated_by")
    private InitiationByEnum initiatedBy;

    private UserReferenceCollection users;

    @Field("conversation_stats")
    private ConversationStats conversationStats;
}
