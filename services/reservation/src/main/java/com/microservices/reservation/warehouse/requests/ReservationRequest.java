package com.microservices.reservation.warehouse.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationRequest(
        Long warehouse, // id of warehouse for reserving
        @NotNull(message = "message")
        LocalDate fromDate,
        @NotNull(message = "message")
        LocalDate toDate,
        Double totalAmount
) {
}
