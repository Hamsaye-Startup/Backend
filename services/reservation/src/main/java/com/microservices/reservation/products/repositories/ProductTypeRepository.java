package com.microservices.reservation.products.repositories;

import com.microservices.reservation.products.models.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
}
