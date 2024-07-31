package com.microservices.reservation.reservations.responses;

import com.microservices.reservation.reservations.models.ForEachDateEnum;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record ReservationWarehouseResponse(
        Long warehouse,
        Date fromDate,
        Date toDate,
        List<Date> InstallmentsDate,
        Double totalFees,
        Double rentPer,
        Double amountPaid,
        ForEachDateEnum perDate,
        Integer totalInstallmentsNumber,
        Integer paidInstallmentsNumber
) {
}
