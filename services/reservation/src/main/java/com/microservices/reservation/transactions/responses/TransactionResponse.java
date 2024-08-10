package com.microservices.reservation.transactions.responses;

import com.microservices.reservation.installments.responses.InstallmentResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
        Long id,
        String type,
        String code,
        InstallmentResponse installment,
        Double siteFees,
        Double discountPercent,
        String discountCode,
        LocalDateTime transferOn
) {
}
