package com.microservices.reservation.reservations.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.reservations.requests.OrderWarehouseRequest;
import com.microservices.reservation.reservations.responses.OrderWarehouseResponse;
import com.microservices.reservation.reservations.services.OrderServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final MessageMapper mapper;
    private final OrderServiceManagement management;

    @PostMapping("/warehouse")
    public ResponseEntity<?> order(OrderWarehouseRequest order) {
        OrderWarehouseResponse response = management.order(order);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
