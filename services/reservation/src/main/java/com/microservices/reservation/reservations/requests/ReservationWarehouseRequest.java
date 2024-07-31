package com.microservices.reservation.reservations.requests;

import com.microservices.reservation.reservations.models.ForEachDateEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
public record ReservationWarehouseRequest(
        Long warehouse,
        @NotNull
        Date fromDate,
        @NotNull
        Date toDate,
        Double totalFees,
        Double rentPer,
        ForEachDateEnum perDate,
        Integer totalInstallmentsNumber
) {
}
