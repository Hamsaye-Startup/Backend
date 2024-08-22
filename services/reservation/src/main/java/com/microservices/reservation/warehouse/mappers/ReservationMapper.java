package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationMapper {

    public ReservationResponse toResponse(ReservationEntity reservation) {
        return ReservationResponse.builder()
                .id(reservation.getUid())
                .reservedBy(reservation.getReservedBy())
                .owner(reservation.getOwner())
                .warehouse(reservation.getWarehouse())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .stats(reservation.getStats())
                .build();
    }
}
