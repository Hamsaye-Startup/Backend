package com.microservices.reservation.reservations.repositories;

import com.microservices.reservation.reservations.models.ReservationWarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationWarehouseRepository extends JpaRepository<ReservationWarehouseEntity, UUID> {

    @Query("select r from ReservationWarehouseEntity r where r.warehouse = :warehouseId")
    List<ReservationWarehouseEntity> findByWarehouse(@Param("warehouseId") Long id);
}
