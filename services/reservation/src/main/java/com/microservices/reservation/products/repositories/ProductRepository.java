package com.microservices.reservation.products.repositories;

import com.microservices.reservation.products.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select p from ProductEntity p where p.reservation = :reservation_id")
    Page<ProductEntity> findAllByReservation(@Param("reservation_id") UUID uid, Pageable pageable);
}
