package com.microservices.reservation.reservations.repositories;

import com.microservices.reservation.reservations.models.ReservationWarehouseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationWarehouseHistoryRepository extends JpaRepository<ReservationWarehouseHistoryEntity, UUID> {
}
