package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.models.StorageVerifiedEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    Optional<StorageEntity> findByIdAndEnabledAndStatusNot(
            Long id,
            boolean enabled,
            StorageStatusEnum status
    );
    Page<StorageEntity> findAllByOwner(UUID owner, Pageable pageable);

    @Query("select s from StorageEntity s where " +
            "s.enabled = :enabled and (" +
            "s.address.address like %:keyword% or " +
            "s.address.details.postalCode like %:keyword%" +
            ")")
    Page<StorageEntity> searchAllByValue(
            @Param("keyword") String value,
            @Param("enabled") boolean enabled,
            Pageable pageable
    );

    Page<StorageEntity> findAllByCategoryAndEnabledAndStatusNot(
            StorageCategoryEnum category,
            boolean enabled,
            StorageStatusEnum status,
            Pageable pageable
    );
}
