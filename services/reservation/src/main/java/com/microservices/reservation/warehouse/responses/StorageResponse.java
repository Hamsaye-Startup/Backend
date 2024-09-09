package com.microservices.reservation.warehouse.responses;

import lombok.Builder;

import java.util.UUID;

/**
 * Represents a response containing details about a storage unit.
 * <p>
 * This record encapsulates information about a storage unit, including its ID, owner, category,
 * dimensions, pricing details, and a description.
 * </p>
 *
 * @param id the unique identifier of the storage unit
 * @param owner the UUID of the owner of the storage unit
 * @param category the category or type of the storage unit
 * @param width the width of the storage unit in units
 * @param height the height of the storage unit in units
 * @param amount the amount or price associated with the storage unit
 * @param discountAmount the discount amount applied to the storage unit
 * @param desc a description of the storage unit
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record StorageResponse(
        Long id,
        UUID owner,
        String category,
        Integer width,
        Integer height,
        Double amount,
        Double discountAmount,
        String desc
) {
}
