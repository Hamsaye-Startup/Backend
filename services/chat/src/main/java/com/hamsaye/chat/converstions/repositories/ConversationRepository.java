package com.hamsaye.chat.converstions.repositories;

import com.hamsaye.chat.converstions.models.ConversationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public interface ConversationRepository extends MongoRepository<ConversationEntity, UUID> {

    @Query("{ $or: [" +
            "{'users.starter.uid': ?0}, " +
            "{'users.continuator.uid': ?0}" +
            "] }")
    List<ConversationEntity> findAllByUsersUid(UUID uid);

    @Query("{ $or: [" +
            "{ $and: [{'users.starter.uid': ?0}, {'users.continuator.uid': ?1}] }, " +
            "{ $and: [{'users.starter.uid': ?1}, {'users.continuator.uid': ?0}] }" +
            "] }")
    Optional<ConversationEntity> findByStarterAndContinuator(UUID starterId, UUID continuatorId);


}
