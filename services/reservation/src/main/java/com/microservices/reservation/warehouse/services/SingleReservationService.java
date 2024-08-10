package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import com.microservices.reservation.warehouse.repositories.SingleReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SingleReservationService {

    private final SingleReservationRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public SingleReservationEntity persist(SingleReservationEntity reservationEntity) {
        return repository.saveAndFlush(reservationEntity);
    }
}
