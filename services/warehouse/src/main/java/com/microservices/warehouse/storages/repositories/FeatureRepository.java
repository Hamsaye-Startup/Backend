package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureEntity, Long> {

    Optional<FeatureEntity> findByCode(String code);
}
