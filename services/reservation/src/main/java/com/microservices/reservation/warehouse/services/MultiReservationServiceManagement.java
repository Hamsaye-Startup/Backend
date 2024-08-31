package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.services.InstallmentService;
import com.microservices.reservation.warehouse.mappers.MultiReservationMapper;
import com.microservices.reservation.warehouse.models.ConfirmReserveEnum;
import com.microservices.reservation.warehouse.models.MultiReservationEntity;
import com.microservices.reservation.warehouse.models.ReservationStats;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static com.microservices.reservation.warehouse.services.ReservationServiceManagement.addReservationStrategy;

@Service
@RequiredArgsConstructor
public class MultiReservationServiceManagement implements ReservationExecutor {

    private final MultiReservationMapper mapper;
    private final MultiReservationService service;

    private final StorageService storageService;
    private final InstallmentService installmentService;


    @Override
    public void register() {
        addReservationStrategy(ReservationStrategyMode.multiple, this);
    }

    @Override
    public ReservationResponse reserve(ReservationRequest reservation) {

        // find the warehouse
        StorageResponse warehouse = storageService.findWarehouseById(reservation.warehouse());

        // find the authorized user

        // map the reservation
        MultiReservationEntity reservationEntity = mapper.toMultiReservationEntity(reservation);

        // persist the installment
        Set<InstallmentEntity> installments = installmentService.calculatePerMonth(
                reservation.totalAmount(),
                warehouse.owner(),
                null,
                reservation.fromDate(),
                reservation.toDate()
        );
        reservationEntity.setInstallments(installments);
        reservationEntity.setStats(ReservationStats.builder()
                .confirmed(ConfirmReserveEnum.NOT_CONFIRMED)
                .build());

        return mapper.toResponse(service.persist(reservationEntity));
    }

    @Override
    public ReservationResponse findById(UUID uid) {
        return mapper.toResponse(service.findById(uid));
    }
}
