package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.repositories.MultiReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MultiReservationService {

    private final MultiReservationRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public MultiReservationEntity persist(MultiReservationEntity reservationEntity) {
        return repository.saveAndFlush(reservationEntity);
    }
}
