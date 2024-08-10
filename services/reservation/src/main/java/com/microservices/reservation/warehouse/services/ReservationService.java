package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByOwner(UUID uid, Pageable pageable) {
        return repository.findByOwnerId(uid, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByRenter(UUID uid, Pageable pageable) {
        return repository.findByRenterId(uid, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<ReservationEntity> findReservationByWarehouse(Long id, Pageable pageable) {
        return repository.findByWarehouse(id, pageable);
    }
}
