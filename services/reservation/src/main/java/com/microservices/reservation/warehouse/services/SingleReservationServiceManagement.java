package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.services.InstallmentService;
import com.microservices.reservation.warehouse.mappers.SingleReservationMapper;
import com.microservices.reservation.warehouse.models.ConfirmReserveEnum;
import com.microservices.reservation.warehouse.models.ReservationStats;
import com.microservices.reservation.warehouse.models.SingleReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.microservices.reservation.warehouse.services.ReservationServiceManagement.addReservationStrategy;

@Service
@RequiredArgsConstructor
public class SingleReservationServiceManagement implements ReservationExecutor {

    private final SingleReservationMapper mapper;
    private final SingleReservationService service;

    private final StorageService storageService;
    private final InstallmentService installmentService;

    @PostConstruct
    @Override
    public void register() {
        addReservationStrategy(ReservationStrategyMode.single, this);
    }

    @Override
    public ReservationResponse reserve(ReservationRequest reservation) {

        // find the warehouse
        StorageResponse warehouse = storageService.findWarehouseById(reservation.warehouse());

        // find the authorized user

        // map the reservation
        SingleReservationEntity reservationEntity = mapper.toSingleReservationEntity(reservation);

        // persist the installment
        InstallmentEntity installment = installmentService.generate(
                reservation.totalAmount(),
                warehouse.owner(),
                null
        );
        reservationEntity.setInstallment(installment);
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
