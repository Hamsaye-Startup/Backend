package com.microservices.warehouse.warehouses.responses;

import lombok.Builder;

@Builder
public record FeatureResponse(
        String code,
        String title,
        String desc
) {
}
