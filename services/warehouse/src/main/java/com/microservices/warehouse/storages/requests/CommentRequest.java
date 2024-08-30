package com.microservices.warehouse.storages.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentRequest(
        UUID reservationId,
        Float score,
        String content
) {
}
