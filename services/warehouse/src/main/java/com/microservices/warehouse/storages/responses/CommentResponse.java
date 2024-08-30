package com.microservices.warehouse.storages.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CommentResponse(
        Long id,
        UUID commentBy,
        Float score,
        String content,
        LocalDateTime commentAt,
        boolean displayable
) {
}
