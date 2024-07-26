package com.microservices.warehouse.warehouses.responses;

import com.microservices.warehouse.warehouses.models.CategoryEnum;
import com.microservices.warehouse.warehouses.models.WarehouseStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
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
        List<WarehouseStatus> status
) {
}
