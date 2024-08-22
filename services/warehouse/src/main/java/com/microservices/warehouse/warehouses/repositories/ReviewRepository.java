package com.microservices.warehouse.warehouses.repositories;

import com.microservices.warehouse.warehouses.models.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByWarehouseId(Long warehouseId);
}
