package com.microservices.reservation.products.responses;

import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.Builder;

@Builder
public record ProductResponse(
        ProductTypeEntity type,
        Double price,
        String desc,
        ReservationResponse reservation
) {
}
