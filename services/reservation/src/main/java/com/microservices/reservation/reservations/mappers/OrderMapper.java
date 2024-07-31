package com.microservices.reservation.reservations.mappers;

import com.microservices.reservation.reservations.requests.OrderWarehouseRequest;
import com.microservices.reservation.reservations.responses.OrderWarehouseResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderWarehouseResponse toResponse(OrderWarehouseRequest request) {
        return OrderWarehouseResponse.builder()
                .warehouse(request.warehouseId())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .rentPer(request.warehouseAmount())
                .perDate(request.perDate())
                .totalInstallmentsNumber(request.totalInstallmentsNumber())
                .build();
    }
}
