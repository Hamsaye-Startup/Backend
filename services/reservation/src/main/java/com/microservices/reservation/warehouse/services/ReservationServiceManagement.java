package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.kafka.producers.StorageProducerService;
import com.microservices.reservation.kafka.requests.ReservationNotifyRequest;
import com.microservices.reservation.kafka.requests.ReservationNotifyType;
import com.microservices.reservation.warehouse.mappers.ReservationMapper;
import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

/**
 * Service class for managing reservations, including processing, retrieving, and accepting or rejecting reservations.
 * <p>
 * This class provides methods to process reservations based on different strategies, retrieve reservations by various
 * criteria, and handle acceptance or rejection of reservations. It interacts with external Kafka services to notify
 * about reservation changes.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceManagement {

    /**
     * Mapper for converting between reservation entities and responses.
     *
     * @see com.microservices.reservation.warehouse.mappers.ReservationMapper
     */
    private final ReservationMapper mapper;

    /**
     * Service for managing reservation persistence and retrieval operations.
     *
     * @see com.microservices.reservation.warehouse.services.ReservationService
     */
    private final ReservationService service;

    /**
     * Service for producing Kafka messages related to storage reservations.
     *
     * @see com.microservices.reservation.kafka.producers.StorageProducerService
     */
    private final StorageProducerService storageProducerService;

    /**
     * Map of reservation strategy modes to their corresponding reservation executors.
     * <p>
     * This static map allows dynamic registration and retrieval of reservation strategies.
     * </p>
     */
    private static HashMap<ReservationStrategyMode, ReservationExecutor> executor = new HashMap<>();

    /**
     * Registers a reservation strategy with the given mode.
     * <p>
     * This method allows adding different reservation strategies to be used based on the reservation mode.
     * </p>
     *
     * @param reservationMode the mode of reservation strategy
     * @param strategy the reservation executor implementing the strategy
     * @since 1.0
     */
    public static void addReservationStrategy(ReservationStrategyMode reservationMode, ReservationExecutor strategy) {
        executor.put(reservationMode, strategy);
    }

    /**
     * Processes a reservation request using the specified reservation strategy.
     * <p>
     * This method delegates the reservation request to the appropriate strategy based on the provided mode.
     * </p>
     *
     * @param reservationMode the mode of reservation strategy
     * @param reservation the reservation request details
     * @param debtor the UUID of the debtor
     * @return the reservation response
     * @since 1.0
     */
    public ReservationResponse processReservation(ReservationStrategyMode reservationMode, ReservationRequest reservation, UUID debtor) {
        return executor.get(reservationMode).reserve(reservation, debtor);
    }

    /**
     * Retrieves reservations by the host's UUID.
     * <p>
     * This method returns a paginated list of reservations where the specified UUID is the host.
     * </p>
     *
     * @param uid the UUID of the host
     * @param pageable pagination information
     * @return a paginated list of reservation responses
     * @since 1.0
     */
    public Page<ReservationResponse> showReservationByHost(UUID uid, Pageable pageable) {
        return service.findReservationByOwner(uid, pageable)
                .map(mapper::toResponse);
    }

    /**
     * Retrieves reservations by the renter's UUID.
     * <p>
     * This method returns a paginated list of reservations where the specified UUID is the renter.
     * </p>
     *
     * @param uid the UUID of the renter
     * @param pageable pagination information
     * @return a paginated list of reservation responses
     * @since 1.0
     */
    public Page<ReservationResponse> showReservationByRenter(UUID uid, Pageable pageable) {
        return service.findReservationByRenter(uid, pageable)
                .map(mapper::toResponse);
    }

    /**
     * Retrieves a reservation by its unique identifier using the specified reservation strategy.
     * <p>
     * This method delegates the retrieval to the appropriate strategy based on the provided mode.
     * </p>
     *
     * @param reservationMode the mode of reservation strategy
     * @param uid the unique identifier of the reservation
     * @return the reservation response
     * @since 1.0
     */
    public ReservationResponse processFindingById(ReservationStrategyMode reservationMode, UUID uid) {
        return executor.get(reservationMode).findById(uid);
    }

    /**
     * Retrieves reservations by the warehouse's ID.
     * <p>
     * This method returns a paginated list of reservations for the specified warehouse ID.
     * </p>
     *
     * @param id the ID of the warehouse
     * @param pageable pagination information
     * @return a paginated list of reservation responses
     * @since 1.0
     */
    public Page<ReservationResponse> showReservationByWarehouse(Long id, Pageable pageable) {
        return service.findReservationByWarehouse(id, pageable)
                .map(mapper::toResponse);
    }

    /**
     * Accepts a reservation by its unique identifier and notifies about the reservation.
     * <p>
     * This method finds the reservation, confirms it, and sends a notification via Kafka.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return the updated reservation response with confirmation
     * @since 1.0
     */
    public ReservationResponse acceptReservation(UUID uid) {

        // find the reservation by id
        ReservationEntity reservation = service.findReservationById(uid);
        ReservationEntity confirmed = service.confirmReservation(reservation, true);

        // generate notification
        storageProducerService.send(ReservationNotifyRequest.builder()
                .reservationId(confirmed.getUid())
                .reservedBy(confirmed.getReservedBy())
                .fromDate(confirmed.getFromDate())
                .toDate(confirmed.getToDate())
                .warehouseId(confirmed.getWarehouse())
                .type(ReservationNotifyType.NEW_RESERVE)
                .message(ReservationNotifyType.NEW_RESERVE.getMessage())
                .build());

        return mapper.toResponse(confirmed);
    }

    /**
     * Rejects a reservation by its unique identifier.
     * <p>
     * This method finds the reservation and confirms it with rejection status.
     * </p>
     *
     * @param uid the unique identifier of the reservation
     * @return the updated reservation response with rejection status
     * @since 1.0
     */
    public ReservationResponse rejectReservation(UUID uid) {

        // find the reservation by id
        ReservationEntity reservation = service.findReservationById(uid);
        return mapper.toResponse(service.confirmReservation(reservation, true));
    }
}
