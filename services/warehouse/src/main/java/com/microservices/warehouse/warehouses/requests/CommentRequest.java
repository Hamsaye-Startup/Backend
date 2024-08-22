package com.microservices.warehouse.warehouses.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentRequest(
        UUID reservationId,
        Float score,
        String content
) {
}
