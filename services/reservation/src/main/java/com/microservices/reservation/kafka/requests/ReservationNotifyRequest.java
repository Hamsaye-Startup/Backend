package com.microservices.reservation.kafka.requests;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservationNotifyRequest(
        UUID reservationId,
        UUID reservedBy,
        Long warehouseId,
        LocalDate fromDate,
        LocalDate toDate,
        String message,
        ReservationNotifyType type
) {
}
