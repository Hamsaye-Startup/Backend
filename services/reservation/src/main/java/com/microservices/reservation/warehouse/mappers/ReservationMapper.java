package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This service class provides mapping functionalities between the reservation entity
 * and reservation response.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ReservationMapper {

    /**
     * Converts a {@link ReservationEntity} to a {@link ReservationResponse}.
     *
     * @param reservation the reservation entity to convert
     * @return the corresponding {@link ReservationResponse}
     * @since 1.0
     */
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
