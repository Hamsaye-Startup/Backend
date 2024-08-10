package com.microservices.reservation.warehouse.responses;

import lombok.Builder;

@Builder
public record PolicyResponse(
        String code,
        String title,
        String desc
) {
}
