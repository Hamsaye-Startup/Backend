package com.microservices.reservation.warehouse.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultiReservationMapper {

    private final InstallmentMapper mapper;

    public MultiReservationEntity toMultiReservationEntity(ReservationRequest request) {
        return MultiReservationEntity.singleBuilder()
                .warehouse(request.warehouse())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .build();
    }

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
