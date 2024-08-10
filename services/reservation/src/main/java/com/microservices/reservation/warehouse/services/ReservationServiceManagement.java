package com.microservices.reservation.warehouse.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.reservation.warehouse.mappers.ReservationMapper;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceManagement {

    private final ReservationMapper mapper;
    private final ReservationService service;

    private static HashMap<ReservationStrategyMode, ReservationExecutor> executor = new HashMap<>();

    public static void addReservationStrategy(ReservationStrategyMode reservationMode, ReservationExecutor strategy) {
        executor.put(reservationMode, strategy);
    }

    public ReservationResponse processReservation(ReservationStrategyMode reservationMode, ReservationRequest reservation) {
        return executor.get(reservationMode).reserve(reservation);
    }

    public Page<ReservationResponse> showReservationByHost(UUID uid, Pageable pageable) {
        return service.findReservationByOwner(uid, pageable)
                .map(mapper::toResponse);
    }

    public Page<ReservationResponse> showReservationByRenter(UUID uid, Pageable pageable) {
        return service.findReservationByRenter(uid, pageable)
                .map(mapper::toResponse);
    }

    public ReservationResponse processFindingById(ReservationStrategyMode reservationMode, UUID uid) {
        return executor.get(reservationMode).findById(uid);
    }

    public Page<ReservationResponse> showReservationByWarehouse(Long id, Pageable pageable) {
        return service.findReservationByWarehouse(id, pageable)
                .map(mapper::toResponse);
    }
}
