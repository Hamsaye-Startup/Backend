package com.microservices.reservation.warehouse.repositories;

import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SingleReservationRepository extends
        JpaRepository<SingleReservationEntity, UUID> {


}
