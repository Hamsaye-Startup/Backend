package com.microservices.warehouse.warehouses.repositories;

import com.microservices.warehouse.warehouses.models.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {

    Optional<PolicyEntity> findByCode(String code);
}
