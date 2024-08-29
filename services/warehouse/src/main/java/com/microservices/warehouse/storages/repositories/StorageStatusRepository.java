package com.microservices.warehouse.storages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageStatusRepository extends JpaRepository<StorageStatusEntity, Long> {
}
