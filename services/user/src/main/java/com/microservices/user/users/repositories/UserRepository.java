package com.microservices.user.users.repositories;

import com.microservices.user.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhone(String phone);

    @Query("select u from UserEntity u order by u.createdAt desc limit 20")
    List<UserEntity> findAllUsers();

    @Query("select u from UserEntity u where u.createdAt > :offsetTime order by u.createdAt desc limit 20")
    List<UserEntity> findAllUsers(@Param("offsetTime") LocalDateTime timestamp);
}
