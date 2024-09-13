package com.microservices.user.users.repositories;

import com.microservices.user.users.models.UserConnectionStatus;
import com.microservices.user.users.models.UserDetailEntity;
import com.microservices.user.users.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    Optional<UserDetailEntity> findByUser(UserEntity user);

    @Query("select u from UserDetailEntity u " +
            "where " +
            "u.user.role.id = :roleId and " +
            "u.connectionStatus = :connectionStatus")
    Page<UserDetailEntity> findAllUsersByRoleIdAndConnectionStatus(
            @Param("roleId") UUID roleId,
            @Param("connectionStatus") UserConnectionStatus connectionStatus,
            Pageable pageable
    );
}
