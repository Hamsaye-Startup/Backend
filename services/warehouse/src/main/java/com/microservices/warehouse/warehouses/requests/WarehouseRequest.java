package com.microservices.warehouse.warehouses.requests;

import com.microservices.warehouse.warehouses.models.CategoryEnum;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record WarehouseRequest(
        UUID owner,
        CategoryEnum category,
        Set<String> features,
        Set<String> policies,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc
) {
}
