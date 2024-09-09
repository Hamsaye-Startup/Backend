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

/**
 * Service class for managing single reservations, including interactions with various components
 * and strategies for reservation handling.
 * <p>
 * This class implements {@link ReservationExecutor} and provides functionality to handle single
 * reservations by interacting with storage services, installment services, and persisting reservation data.
 * </p>
 *
 * @see com.microservices.reservation.warehouse.services.ReservationExecutor
 * @see com.microservices.reservation.warehouse.services.SingleReservationService
 * @see com.microservices.reservation.warehouse.services.StorageService
 * @see com.microservices.reservation.installments.services.InstallmentService
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SingleReservationServiceManagement implements ReservationExecutor {

    /**
     * Mapper for converting between reservation requests and reservation entities.
     *
     * @see com.microservices.reservation.warehouse.mappers.SingleReservationMapper
     */
    private final SingleReservationMapper mapper;

    /**
     * Service for handling single reservation persistence operations.
     *
     * @see com.microservices.reservation.warehouse.services.SingleReservationService
     */
    private final SingleReservationService service;

    /**
     * Service for interacting with storage information and retrieving details about warehouses.
     *
     * @see com.microservices.reservation.warehouse.services.StorageService
     */
    private final StorageService storageService;

    /**
     * Service for handling installment operations related to reservations.
     *
     * @see com.microservices.reservation.installments.services.InstallmentService
     */
    private final InstallmentService installmentService;

    /**
     * Registers this reservation strategy for single reservations during the initialization of the service.
     * <p>
     * This method is called after the bean's properties have been set and is used to register the single
     * reservation strategy with the global reservation management system.
     * </p>
     */
    @PostConstruct
    @Override
    public void register() {
        addReservationStrategy(ReservationStrategyMode.single, this);
    }

    /**
     * Reserves a storage based on the provided reservation request and debtor information.
     * <p>
     * This method finds the relevant warehouse, maps the reservation request to a reservation entity,
     * generates an installment, and persists the reservation. The reservation status is initially set
     * to {@link ConfirmReserveEnum#NOT_CONFIRMED}.
     * </p>
     *
     * @param reservation the reservation request containing details for the reservation
     * @param debtor the unique identifier of the debtor
     * @return a {@link ReservationResponse} containing the details of the persisted reservation
     */
    @Override
    public ReservationResponse reserve(ReservationRequest reservation, UUID debtor) {

        // find the warehouse
        StorageResponse warehouse = storageService.findWarehouseById(reservation.warehouse());

        // map the reservation
        SingleReservationEntity reservationEntity = mapper.toSingleReservationEntity(reservation);

        // persist the installment
        InstallmentEntity installment = installmentService.generate(
                reservation.totalAmount(),
                warehouse.owner(),
                debtor
        );
        reservationEntity.setInstallment(installment);
        reservationEntity.setStats(ReservationStats.builder()
                .confirmed(ConfirmReserveEnum.NOT_CONFIRMED)
                .build());

        return mapper.toResponse(service.persist(reservationEntity));
    }

    /**
     * Finds a reservation by its unique identifier.
     * <p>
     * This method retrieves a reservation entity by its ID and maps it to a {@link ReservationResponse}.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return a {@link ReservationResponse} containing the details of the reservation
     */
    @Override
    public ReservationResponse findById(UUID uid) {
        return mapper.toResponse(service.findById(uid));
    }
}
