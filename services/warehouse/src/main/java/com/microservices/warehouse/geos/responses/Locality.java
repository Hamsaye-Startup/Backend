package com.microservices.warehouse.geos.responses;

import lombok.Builder;

@Builder
public record Locality(
        String city,
        String region,
        String neighborhood,
        String primary,
        String plaque
) {
}
