package com.microservices.reservation.warehouse.repositories;

import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface MultiReservationRepository
        extends JpaRepository<MultiReservationEntity, UUID> {

}
