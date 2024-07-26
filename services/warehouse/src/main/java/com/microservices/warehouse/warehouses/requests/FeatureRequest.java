package com.microservices.warehouse.warehouses.requests;

import lombok.Builder;

@Builder
public record FeatureRequest(
        String code,
        String title,
        String desc
) {
}
