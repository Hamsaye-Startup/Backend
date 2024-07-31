package com.microservices.reservation.reservations.requests;

import com.microservices.reservation.reservations.models.ForEachDateEnum;
import lombok.Builder;

import java.util.Date;

@Builder
public record OrderWarehouseRequest(
        Long warehouseId,
        Date fromDate,
        Date toDate,
        Double warehouseAmount,
        ForEachDateEnum perDate,
        Integer totalInstallmentsNumber
) {
}
