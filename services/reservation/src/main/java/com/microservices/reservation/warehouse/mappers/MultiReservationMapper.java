package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * This service class provides mapping functionalities between the reservation request,
 * reservation entity, and reservation response for multi-reservation scenarios.
 *
 * <p>It converts {@link ReservationRequest} objects to {@link MultiReservationEntity}
 * objects and maps {@link MultiReservationEntity} objects to {@link ReservationResponse}
 * objects. It also utilizes {@link InstallmentMapper} for converting installments.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MultiReservationMapper {

    private final InstallmentMapper mapper;

    /**
     * Converts a {@link ReservationRequest} to a {@link MultiReservationEntity}.
     *
     * @param request the reservation request to convert
     * @return the corresponding {@link MultiReservationEntity}
     * @since 1.0
     */
    public MultiReservationEntity toMultiReservationEntity(ReservationRequest request) {
        return MultiReservationEntity.singleBuilder()
                .warehouse(request.warehouse())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .build();
    }

    /**
     * Converts a {@link MultiReservationEntity} to a {@link ReservationResponse}.
     *
     * @param reservation the reservation entity to convert
     * @return the corresponding {@link ReservationResponse}
     * @since 1.0
     */
    public ReservationResponse toResponse(MultiReservationEntity reservation) {
        return ReservationResponse.builder()
                .id(reservation.getUid())
                .reservedBy(reservation.getReservedBy())
                .owner(reservation.getOwner())
                .warehouse(reservation.getWarehouse())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .factor(mapper.toFactor(reservation.getInstallments()
                        .stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toSet())
                ))
                .stats(reservation.getStats())
                .build();
    }
}
