package com.microservices.reservation.reservations.services;

import com.microservices.reservation.reservations.exceptions.NotFoundReservationException;
import com.microservices.reservation.reservations.models.ReservationWarehouseEntity;
import com.microservices.reservation.reservations.repositories.ReservationWarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationWarehouseRepository repository;
    @Transactional(propagation = Propagation.REQUIRED)
    public ReservationWarehouseEntity persist(ReservationWarehouseEntity reservationEntity) {
        return repository.saveAndFlush(reservationEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ReservationWarehouseEntity> findByWarehouse(Long id) {
        return new ArrayList<>(repository.findByWarehouse(id));
    }
}
