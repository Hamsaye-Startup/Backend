package com.microservices.reservation.installments.responses;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record InstallmentResponse(
        Double value,
        LocalDateTime paymentDue,
        boolean paid,
        UUID debtor,
        UUID creditor
) {
}
