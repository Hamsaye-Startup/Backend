package com.microservices.warehouse.warehouses.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CommentResponse(
        UUID commentBy,
        Float score,
        String content,
        LocalDateTime commentAt
) {
}
