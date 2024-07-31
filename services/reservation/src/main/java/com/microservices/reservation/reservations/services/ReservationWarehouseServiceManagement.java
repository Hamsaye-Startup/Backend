package com.microservices.reservation.reservations.services;

import com.microservices.reservation.reservations.mappers.ReservationMapper;
import com.microservices.reservation.reservations.models.ReservationWarehouseEntity;
import com.microservices.reservation.reservations.requests.ReservationWarehouseRequest;
import com.microservices.reservation.reservations.responses.ReservationWarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationWarehouseServiceManagement {

    private final ReservationMapper mapper;
    private final ReservationService reservationService;
    private final TimePartitionService partitionService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ReservationWarehouseResponse reserve(ReservationWarehouseRequest reservation) {
        // map the request to reservation entity
        ReservationWarehouseEntity reservationEntity = mapper.toReservationEntity(reservation);
        long days = partitionService.differenceInDay(
                reservationEntity.getToDate().getTime() - reservationEntity.getFromDate().getTime()
        );

        // TODO: get the principal or authentication
        reservationEntity.setReservedBy(UUID.fromString(""));
        reservationEntity.setAmountPaid(0D);
        reservationEntity.setInstallmentsPaidNum(0);
        reservationEntity.setInstallmentsDate(
                partitionService.calculateListOfInstallmentDates(
                        days,
                        reservationEntity.getTotalInstallmentsNum(),
                        reservationEntity.getFromDate()
                )
        );

        return mapper.toResponse(reservationService.persist(reservationEntity));
    }

    public List<ReservationWarehouseResponse> findByWarehouse(Long id) {
        return reservationService.findByWarehouse(id).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationWarehouseResponse> findByHost(UUID uid) {
        return null;
    }

    public List<ReservationWarehouseResponse> findByRenter(UUID uid) {
        return null;
    }
}
