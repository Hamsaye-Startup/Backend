package com.microservices.warehouse.warehouses.responses;

import com.microservices.warehouse.warehouses.models.CategoryEnum;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record WarehouseResponse (
        Long id,
        UUID owner,
        CategoryEnum category,
        Set<FeatureResponse> features,
        Set<PolicyResponse> policies,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc,
        boolean marked,
        boolean liked
) {
}
