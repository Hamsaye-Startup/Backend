package com.microservices.reservation.warehouse.repositories;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

    @Query("select r from ReservationEntity r where r.warehouse = :warehouseId")
    Page<ReservationEntity> findByWarehouse(@Param("warehouseId") Long id, Pageable pageable);

    @Query("select r from ReservationEntity r where r.owner = :ownerId")
    Page<ReservationEntity> findByOwnerId(@Param("ownerId") UUID uid, Pageable pageable);

    @Query("select r from ReservationEntity r where r.reservedBy = :renterId")
    Page<ReservationEntity> findByRenterId(@Param("renterId") UUID uid, Pageable pageable);
}
