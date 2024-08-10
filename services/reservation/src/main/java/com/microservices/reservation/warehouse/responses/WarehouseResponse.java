package com.microservices.reservation.warehouse.responses;

import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record WarehouseResponse(
        Long id,
        UUID owner,
        String category,
        Set<FeatureResponse> features,
        Set<PolicyResponse> policies,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc,
        List<String> status,
        boolean marked,
        boolean liked
) {
}
