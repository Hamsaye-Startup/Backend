package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StorageFeatureRepository extends JpaRepository<StorageFeatureEntity, UUID> {

    @Query("SELECT f FROM StorageFeatureEntity f WHERE f.title = :title")
    Optional<StorageFeatureEntity> selectByTitle(@Param("title") String title);
}
