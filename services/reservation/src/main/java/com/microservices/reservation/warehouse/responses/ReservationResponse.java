package com.microservices.reservation.warehouse.responses;

import com.microservices.reservation.installments.responses.InstallmentFactor;
import com.microservices.reservation.warehouse.models.ReservationStats;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a response containing details about a reservation.
 * <p>
 * This record provides comprehensive information about a reservation, including its ID,
 * the person who made the reservation, the associated warehouse, reservation dates,
 * installment factors, and reservation statistics.
 * </p>
 *
 * @param id the unique identifier of the reservation
 * @param reservedBy the UUID of the person who made the reservation
 * @param warehouse the ID of the warehouse associated with the reservation
 * @param owner the UUID of the owner of the warehouse
 * @param fromDate the start date of the reservation
 * @param toDate the end date of the reservation
 * @param factor the installment factor related to the reservation
 * @param stats the current reservation statistics
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @see com.microservices.reservation.warehouse.models.ReservationStats
 * @see com.microservices.reservation.installments.responses.InstallmentFactor
 */
@Builder
public record ReservationResponse(
        UUID id,
        UUID reservedBy,
        Long warehouse,
        UUID owner,
        LocalDate fromDate,
        LocalDate toDate,
        InstallmentFactor factor,
        ReservationStats stats
) {}
