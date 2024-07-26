package com.microservices.warehouse.warehouses.responses;

import lombok.Builder;

@Builder
public record PolicyResponse(
        String code,
        String title,
        String desc
) {
}
