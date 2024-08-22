package com.microservices.warehouse.warehouses.responses;

import com.microservices.warehouse.warehouses.models.CategoryEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LimitedWarehouseResponse(
        Long id,
        UUID owner,
        CategoryEnum category,
        LocalDateTime createAt,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        boolean marked,
        boolean liked
) {
}
