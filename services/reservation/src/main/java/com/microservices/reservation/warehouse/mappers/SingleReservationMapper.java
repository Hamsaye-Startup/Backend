package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This service class provides mapping functionalities between the single reservation entity
 * and reservation response.
 *
 * <p>It converts {@link ReservationRequest} objects to {@link SingleReservationEntity} objects
 * and {@link SingleReservationEntity} objects to {@link ReservationResponse} objects.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SingleReservationMapper {

    /**
     * @see com.microservices.reservation.installments.mappers.InstallmentMapper
     */
    private final InstallmentMapper mapper;

    /**
     * Converts a {@link ReservationRequest} to a {@link SingleReservationEntity}.
     *
     * @param request the reservation request to convert
     * @return the corresponding {@link SingleReservationEntity}
     * @since 1.0
     */
    public SingleReservationEntity toSingleReservationEntity(ReservationRequest request) {
        return SingleReservationEntity.singleBuilder()
                .warehouse(request.warehouse())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .build();
    }

    /**
     * Converts a {@link SingleReservationEntity} to a {@link ReservationResponse}.
     *
     * @param reservation the single reservation entity to convert
     * @return the corresponding {@link ReservationResponse}
     * @since 1.0
     */
    public ReservationResponse toResponse(SingleReservationEntity reservation) {
        return ReservationResponse.builder()
                .id(reservation.getUid())
                .reservedBy(reservation.getReservedBy())
                .owner(reservation.getOwner())
                .warehouse(reservation.getWarehouse())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .factor(mapper.toFactor(mapper.toResponse(reservation.getInstallment())))
                .stats(reservation.getStats())
                .build();
    }
}
