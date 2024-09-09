package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByStorage(StorageEntity storage, Pageable pageable);

    Page<CommentEntity> findAllByStorageAndEnabled(StorageEntity storage, boolean enabled, Pageable pageable);
}
