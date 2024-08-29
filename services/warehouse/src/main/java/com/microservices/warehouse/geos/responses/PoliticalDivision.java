package com.microservices.warehouse.geos.responses;

import lombok.Builder;

@Builder
public record PoliticalDivision(
        String country,
        String province,
        String county
) {
}
