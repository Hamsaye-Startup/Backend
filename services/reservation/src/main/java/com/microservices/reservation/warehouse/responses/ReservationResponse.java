package com.microservices.reservation.warehouse.responses;

import com.microservices.reservation.installments.responses.InstallmentFactor;
import com.microservices.reservation.warehouse.models.ReservationStats;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservationResponse(
        UUID id,
        UUID reservedBy,
        Long warehouse,
        UUID owner,
        LocalDate fromDate,
        LocalDate toDate,
        InstallmentFactor factor,
        ReservationStats stats

) {}
