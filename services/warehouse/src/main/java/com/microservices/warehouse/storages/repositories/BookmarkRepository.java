package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkId> {

    @Query("select b from BookmarkEntity b where b.id.userId = :userId order by b.createdAt desc")
    List<BookmarkEntity> findByUserId(@Param("userId") UUID userId);

    @Query("select b from BookmarkEntity b where b.id.userId = :userId order by b.createdAt desc")
    Page<BookmarkEntity> findByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("select b from BookmarkEntity b where b.id.userId = :userId and b.id.storage.id = :storageId")
    Optional<BookmarkEntity> findByUserIdAndStorageId(@Param("userId") UUID userId, @Param("storageId") Long storageId);

}
