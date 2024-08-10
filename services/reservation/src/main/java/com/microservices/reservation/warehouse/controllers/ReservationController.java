package com.microservices.reservation.warehouse.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import com.microservices.reservation.warehouse.services.ReservationServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/reservation/warehouse")
@RequiredArgsConstructor
public class ReservationController {

    private final MessageMapper mapper;
    private final ReservationServiceManagement management;

    @PostMapping("/mode/{mode}")
    public ResponseEntity<?> reserve(
            @PathVariable("mode") ReservationStrategyMode mode,
            @RequestBody ReservationRequest reservation) {

        ReservationResponse response = management.processReservation(mode, reservation);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<?> findReservationsByWarehouse(@PathVariable("id") Long id, Pageable pageable) {
        Page<ReservationResponse> responses = management.showReservationByWarehouse(id, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<?> findReservationsByHost(@PathVariable("id") UUID uid, Pageable pageable) {
        Page<ReservationResponse> responses = management.showReservationByHost(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/renter/{id}")
    public ResponseEntity<?> findReservationsByRenter(@PathVariable("id") UUID uid, Pageable pageable) {
        Page<ReservationResponse> responses = management.showReservationByRenter(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/mode/{mode}/id/{id}")
    public ResponseEntity<?> findReservationById(
            @PathVariable("mode") ReservationStrategyMode mode,
            @PathVariable("id") UUID uid) {

        ReservationResponse response = management.processFindingById(mode, uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
