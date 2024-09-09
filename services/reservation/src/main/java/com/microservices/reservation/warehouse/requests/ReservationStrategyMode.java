package com.microservices.reservation.warehouse.requests;

/**
 * Defines the different strategies available for reserving storage.
 * <p>
 * This enum represents the modes of reservation that can be applied,
 * which include:
 * <ul>
 *   <li><b>single</b>: Represents a reservation strategy where a single installment is used.</li>
 *   <li><b>multiple</b>: Represents a reservation strategy where multiple installments are used.</li>
 * </ul>
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public enum ReservationStrategyMode {
    single,  // Single installment reservation strategy
    multiple // Multiple installment reservation strategy
}
