package org.hamsaye.storages.daos;

import org.hamsaye.storages.models.StorageFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StorageFeatureRepository extends JpaRepository<StorageFeatureEntity, UUID> {
}
