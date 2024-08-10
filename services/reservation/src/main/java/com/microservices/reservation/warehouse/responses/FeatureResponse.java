package com.microservices.reservation.warehouse.responses;

import lombok.Builder;

@Builder
public record FeatureResponse(
        String code,
        String title,
        String desc
) {
}
