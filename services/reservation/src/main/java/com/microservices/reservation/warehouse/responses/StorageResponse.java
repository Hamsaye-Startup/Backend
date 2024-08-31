package com.microservices.reservation.warehouse.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StorageResponse(
        Long id,
        UUID owner,
        String category,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc
) {
}
