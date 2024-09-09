package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.responses.ReservationResponse;

import java.util.UUID;

/**
 * Interface for executing reservation operations within the warehouse service.
 * <p>
 * This interface defines methods for reserving storage and retrieving reservation details. It supports
 * different strategies for reservation, such as single installment and multiple installments, following
 * the strategy design pattern.
 * </p>
 *
 * <p>
 * Implementations of this interface will provide the concrete logic for reserving storage and managing
 * reservations based on the specific strategy employed.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public interface ReservationExecutor {

    /**
     * Registers a new reservation strategy.
     * <p>
     * This method is used to add or update the strategy for reserving storage. It allows the system to
     * adapt to different reservation approaches, such as single installment or multiple installments.
     * </p>
     * @since 1.0
     */
    void register();

    /**
     * Reserves storage based on the provided reservation request and debtor information.
     *
     * @param reservation the details of the reservation request
     * @param debtor the UUID of the debtor making the reservation
     * @return a {@link ReservationResponse} containing the details of the reservation
     * @since 1.0
     */
    ReservationResponse reserve(ReservationRequest reservation, UUID debtor);

    /**
     * Finds a reservation by its unique identifier.
     *
     * @param uid the UUID of the reservation to find
     * @return a {@link ReservationResponse} containing the details of the reservation
     * @since 1.0
     */
    ReservationResponse findById(UUID uid);
}
