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

/**
 * Service class for managing multiple reservation operations, including reserving and retrieving multiple reservations.
 * <p>
 * This class provides methods to handle reservations with multiple installments. It interacts with the underlying
 * services to persist reservation data, manage installments, and retrieve reservation details.
 * </p>
 *
 * @see com.microservices.reservation.warehouse.mappers.MultiReservationMapper
 * @see com.microservices.reservation.warehouse.services.MultiReservationService
 * @see com.microservices.reservation.warehouse.services.StorageService
 * @see com.microservices.reservation.installments.services.InstallmentService
 * @see com.microservices.reservation.warehouse.models.MultiReservationEntity
 * @see com.microservices.reservation.warehouse.requests.ReservationRequest
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MultiReservationServiceManagement implements ReservationExecutor {

    /**
     * Mapper for converting between reservation request data and multi-reservation entity.
     *
     * @see com.microservices.reservation.warehouse.mappers.MultiReservationMapper
     */
    private final MultiReservationMapper mapper;

    /**
     * Service for persisting and retrieving multi-reservation entities.
     *
     * @see com.microservices.reservation.warehouse.services.MultiReservationService
     */
    private final MultiReservationService service;

    /**
     * Service for interacting with storage data.
     *
     * @see com.microservices.reservation.warehouse.services.StorageService
     */
    private final StorageService storageService;

    /**
     * Service for managing installment calculations and persistence.
     *
     * @see com.microservices.reservation.installments.services.InstallmentService
     */
    private final InstallmentService installmentService;

    /**
     * Registers this service as a strategy for handling multiple reservations.
     * <p>
     * This method is invoked to register the multiple reservation strategy with the reservation service management.
     * </p>
     *
     * @since 1.0
     */
    @Override
    public void register() {
        addReservationStrategy(ReservationStrategyMode.multiple, this);
    }

    /**
     * Reserves storage for the given reservation request with multiple installments.
     * <p>
     * This method finds the storage based on the provided request, calculates installments, maps the reservation request
     * to a multi-reservation entity, and persists it. The reservation status is initially set to not confirmed.
     * </p>
     *
     * @param reservation the reservation request data
     * @param debtor the UUID of the debtor making the reservation
     * @return the response containing details of the reserved storage
     * @since 1.0
     */
    @Override
    public ReservationResponse reserve(ReservationRequest reservation, UUID debtor) {

        // find the warehouse
        StorageResponse warehouse = storageService.findWarehouseById(reservation.warehouse());

        // find the authorized user

        // map the reservation
        MultiReservationEntity reservationEntity = mapper.toMultiReservationEntity(reservation);

        // persist the installment
        Set<InstallmentEntity> installments = installmentService.calculatePerMonth(
                reservation.totalAmount(),
                warehouse.owner(),
                debtor,
                reservation.fromDate(),
                reservation.toDate()
        );
        reservationEntity.setInstallments(installments);
        reservationEntity.setStats(ReservationStats.builder()
                .confirmed(ConfirmReserveEnum.NOT_CONFIRMED)
                .build());

        return mapper.toResponse(service.persist(reservationEntity));
    }

    /**
     * Finds a reservation by its unique identifier.
     * <p>
     * This method retrieves the reservation entity with the specified ID and maps it to a response.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return the response containing details of the reservation
     * @since 1.0
     */
    @Override
    public ReservationResponse findById(UUID uid) {
        return mapper.toResponse(service.findById(uid));
    }
}
