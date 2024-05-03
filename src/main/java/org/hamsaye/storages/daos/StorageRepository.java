package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, UUID> {

    @Query("SELECT s FROM StorageEntity s WHERE s.name = :name")
    Optional<StorageEntity> selectByName(@Param("name") String name);
}
