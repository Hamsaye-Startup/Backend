package com.microservices.user.roles.repositories;

import com.microservices.user.roles.model.RoleEntity;
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
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByName(String name);

    @Query("select r from RoleEntity r order by r.createdAt desc limit 20")
    List<RoleEntity> findAllRoles();

    @Query("select r from RoleEntity r where r.createdAt > :offsetTime order by r.createdAt desc limit 20")
    List<RoleEntity> findAllRoles(@Param("offsetTime") LocalDateTime offset);
}
