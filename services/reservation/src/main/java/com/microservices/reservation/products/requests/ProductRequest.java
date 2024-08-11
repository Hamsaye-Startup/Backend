package com.microservices.reservation.products.requests;

import lombok.Builder;

@Builder
public record ProductRequest(
        Long type,
        Double price,
        String desc
) {
}
