package com.microservices.reservation.warehouse.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.reservation.warehouse.mappers.ReservationMapper;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ReservationServiceManagement {

    private final ReservationMapper mapper;

    private static HashMap<ReservationStrategyMode, ReservationExecutor> executor = new HashMap<>();

    public static void addReservationStrategy(ReservationStrategyMode reservationMode, ReservationExecutor strategy) {
        executor.put(reservationMode, strategy);
    }

    public String processReservation(ReservationStrategyMode reservationMode, ReservationRequest reservation) throws JsonProcessingException {
        ReservationResponse response = executor.get(reservationMode).reserve(reservation);
        return mapper.toString(response);
    }
}
