package com.microservices.reservation.reservations.services;

import com.microservices.reservation.reservations.mappers.OrderMapper;
import com.microservices.reservation.reservations.requests.OrderWarehouseRequest;
import com.microservices.reservation.reservations.responses.OrderWarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceManagement {

    private final OrderMapper mapper;
    private final FinancialWarehouseService service;

    public OrderWarehouseResponse order(OrderWarehouseRequest order) {
        // map the response
        OrderWarehouseResponse response = mapper.toResponse(order);

        // calculate the total fees & rent per total installment
        service.calculateTotalFees(response);
        return response;
    }
}
