package com.microservices.warehouse.storages.requests;

import lombok.Builder;

@Builder
public record StorageRequest(
        String category,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc
) {
}
