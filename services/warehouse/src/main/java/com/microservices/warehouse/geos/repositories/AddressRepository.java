package com.microservices.warehouse.geos.repositories;

import com.microservices.warehouse.geos.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
