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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "col_message")
public class MessageEntity {

    @Id
    private String id;

    @Field("sender_id")
    private UUID senderId;

    @Field("conversation_id")
    @Indexed(unique = true, name = "message_conversation_id_unique")
    private UUID conversationId;

    private String content;

    @Field("sender_at")
    private LocalDateTime sendAt;
}
