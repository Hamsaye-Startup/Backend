package com.hamsaye.chat.users.repositories;

import com.hamsaye.chat.users.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public interface UserRepository extends MongoRepository<UserEntity, UUID> {
}
