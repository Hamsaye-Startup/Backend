package com.microservices.reservation.installments.responses;

import lombok.Builder;

import java.util.Set;

@Builder
public record InstallmentFactor(
        Set<InstallmentResponse> installments,
        int total
) {
}
