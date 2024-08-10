package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SingleReservationMapper {

    private final InstallmentMapper mapper;

    public SingleReservationEntity toSingleReservationEntity(ReservationRequest request) {
        return SingleReservationEntity.singleBuilder()
                .warehouse(request.warehouse())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .build();
    }

    public ReservationResponse toResponse(SingleReservationEntity reservation) {
        return ReservationResponse.builder()
                .id(reservation.getUid())
                .reservedBy(reservation.getReservedBy())
                .owner(reservation.getOwner())
                .warehouse(reservation.getWarehouse())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .factor(mapper.toFactor(mapper.toResponse(reservation.getInstallment())))
                .build();
    }
}
