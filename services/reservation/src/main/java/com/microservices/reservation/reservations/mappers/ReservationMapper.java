package com.microservices.reservation.reservations.mappers;

import com.microservices.reservation.reservations.models.ReservationWarehouseEntity;
import com.microservices.reservation.reservations.requests.ReservationWarehouseRequest;
import com.microservices.reservation.reservations.responses.ReservationWarehouseResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public ReservationWarehouseEntity toReservationEntity(ReservationWarehouseRequest request) {
        return ReservationWarehouseEntity.builder()
                .warehouse(request.warehouse())
                .fromDate(request.fromDate())
                .toDate(request.toDate())
                .totalFees(request.totalFees())
                .rentPer(request.rentPer())
                .perDate(request.perDate())
                .totalInstallmentsNum(request.totalInstallmentsNumber())
                .build();
    }

    public ReservationWarehouseResponse toResponse(ReservationWarehouseEntity reservation) {
        return ReservationWarehouseResponse.builder()
                .warehouse(reservation.getWarehouse())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .InstallmentsDate(reservation.getInstallmentsDate())
                .totalFees(reservation.getTotalFees())
                .rentPer(reservation.getRentPer())
                .amountPaid(reservation.getAmountPaid())
                .perDate(reservation.getPerDate())
                .totalInstallmentsNumber(reservation.getTotalInstallmentsNum())
                .paidInstallmentsNumber(reservation.getInstallmentsPaidNum())
                .build();
    }
}
