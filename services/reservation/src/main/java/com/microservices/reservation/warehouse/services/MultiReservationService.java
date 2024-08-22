package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.exceptions.NotFoundReservationException;
import com.microservices.reservation.warehouse.exceptions.PersistReservationException;
import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.repositories.MultiReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MultiReservationService {

    private final MultiReservationRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public MultiReservationEntity persist(MultiReservationEntity reservationEntity) {
        try {
            return repository.saveAndFlush(reservationEntity);
        } catch (RuntimeException ex) {
            throw new PersistReservationException(ex.getCause(), reservationEntity.getReservedBy().toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public MultiReservationEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundReservationException(uid.toString()));
    }
}
