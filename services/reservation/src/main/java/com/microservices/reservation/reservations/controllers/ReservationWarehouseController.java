package com.microservices.reservation.reservations.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.reservations.requests.ReservationWarehouseRequest;
import com.microservices.reservation.reservations.responses.ReservationWarehouseResponse;
import com.microservices.reservation.reservations.services.ReservationWarehouseServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/reservation/warehouse")
@RequiredArgsConstructor
public class ReservationWarehouseController {

    private final MessageMapper mapper;
    private final ReservationWarehouseServiceManagement management;

    @PostMapping
    public ResponseEntity<?> reserve(ReservationWarehouseRequest reservation) {
        ReservationWarehouseResponse response = management.reserve(reservation);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> showReservationByWarehouse(@PathVariable("id") Long id) {
        List<ReservationWarehouseResponse> responses = management.findByWarehouse(id);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/host/id/{id}")
    public ResponseEntity<?> showReservationsByHost(@PathVariable("id") UUID uid) {
        List<ReservationWarehouseResponse> responses = management.findByHost(uid);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/renter/id/{id}")
    public ResponseEntity<?> showReservationsByRenter(@PathVariable("id") UUID uid) {
        List<ReservationWarehouseResponse> responses = management.findByRenter(uid);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
