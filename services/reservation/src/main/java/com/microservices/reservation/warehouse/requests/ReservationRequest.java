package com.microservices.reservation.warehouse.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Represents a request for creating a reservation.
 * <p>
 * This record captures all necessary details required to make a reservation, including
 * the warehouse ID, reservation start and end dates, and the total amount for the reservation.
 * The `fromDate` and `toDate` fields are mandatory and must not be null.
 * </p>
 *
 * @param warehouse the ID of the warehouse to be reserved
 * @param fromDate the start date of the reservation (must not be null)
 * @param toDate the end date of the reservation (must not be null)
 * @param totalAmount the total amount for the reservation
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ReservationRequest(
        Long warehouse, // id of warehouse for reserving
        @NotNull(message = "message")
        LocalDate fromDate,
        @NotNull(message = "message")
        LocalDate toDate,
        Double totalAmount
) {
}
