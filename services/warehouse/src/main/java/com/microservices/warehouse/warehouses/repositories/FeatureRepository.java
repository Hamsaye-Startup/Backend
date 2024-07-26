package com.microservices.warehouse.warehouses.repositories;

import com.microservices.warehouse.warehouses.models.FeatureEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureEntity, Long> {

    Optional<FeatureEntity> findByCode(String code);

    @Override
    Page<FeatureEntity> findAll(@NonNull Pageable pageable);
}
