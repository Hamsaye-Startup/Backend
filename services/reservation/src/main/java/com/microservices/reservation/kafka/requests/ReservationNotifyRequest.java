package com.microservices.reservation.kafka.requests;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

/*
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
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
