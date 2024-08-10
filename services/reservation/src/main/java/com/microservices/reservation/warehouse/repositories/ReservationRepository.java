package com.microservices.reservation.warehouse.repositories;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

    @Query("select r from ReservationEntity r where r.warehouse = :warehouseId")
    List<ReservationEntity> findByWarehouse(@Param("warehouseId") Long id);

    @Query("select r from ReservationEntity r where r.owner = :ownerId")
    List<ReservationEntity> findByOwnerId(@Param("ownerId") UUID uid);

    @Query("select r from ReservationEntity r where r.reservedBy = :renterId")
    List<ReservationEntity> findByRenterId(@Param("renterId") UUID uid);
}
