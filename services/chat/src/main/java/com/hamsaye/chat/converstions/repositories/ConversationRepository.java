package com.hamsaye.chat.converstions.repositories;

import com.hamsaye.chat.converstions.models.ConversationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends MongoRepository<ConversationEntity, UUID> {

    @Query("db.collection('col_conversation').find({ $or: [{ users.starter: ?0 }, { users.continuator: ?0 }] })")
    List<ConversationEntity> findAllByUsersUid(UUID uid);

    @Query("db.collection('col_conversation').find({ $or: [{ users.starter: ?0, users.continuator: ?1 }, { users.continuator: ?0, users.starter: ?1 }] })")
    Optional<ConversationEntity> findByStarterAndContinuator(UUID starterId, UUID continuatorId);
}
