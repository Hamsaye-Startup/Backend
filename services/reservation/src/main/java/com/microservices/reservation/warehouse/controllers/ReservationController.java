package com.microservices.reservation.warehouse.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.services.ReservationServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reservation/warehouse")
@RequiredArgsConstructor
public class ReservationController {

    private final MessageMapper mapper;
    private final ReservationServiceManagement management;

    @PostMapping("/mode/{mode}")
    public ResponseEntity<?> reserve(
            @PathVariable("mode") ReservationStrategyMode mode,
            @RequestBody ReservationRequest reservation) throws JsonProcessingException {

        String response = management.processReservation(mode, reservation);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
