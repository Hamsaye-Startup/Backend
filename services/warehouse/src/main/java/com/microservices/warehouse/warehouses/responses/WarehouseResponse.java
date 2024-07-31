package com.microservices.warehouse.warehouses.responses;

import com.microservices.warehouse.warehouses.models.CategoryEnum;
import com.microservices.warehouse.warehouses.models.WarehouseStatus;
import lombok.Builder;

import java.util.List;
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
        List<WarehouseStatus> status,
        boolean marked,
        boolean liked
) {
}
