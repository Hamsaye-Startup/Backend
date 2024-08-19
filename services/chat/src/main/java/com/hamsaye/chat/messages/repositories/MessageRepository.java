package com.hamsaye.chat.messages.repositories;

import com.hamsaye.chat.messages.models.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageEntity, Long> {

    Page<MessageEntity> findAllByConversationId(UUID conversationId, Pageable pageable);

    void deleteAllByConversationId(UUID uid);
}
