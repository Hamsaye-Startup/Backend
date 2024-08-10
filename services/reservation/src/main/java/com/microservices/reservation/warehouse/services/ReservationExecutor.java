package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;

import java.util.UUID;

public interface ReservationExecutor {

    void register();
    ReservationResponse reserve(ReservationRequest reservation);
    ReservationResponse findById(UUID uid);
}
