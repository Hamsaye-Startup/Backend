package com.microservices.warehouse.storages.responses;

import com.microservices.warehouse.geos.responses.AddressResponse;
import com.microservices.warehouse.storages.models.Score;
import lombok.Builder;

import java.util.UUID;

/**
 * This record represents a response object for a storage entity.
 * It provides detailed information about the storage, including its ID, owner, category, dimensions, financial details, and associated flags and score.
 *
 * <p>NOTE: Clients can view this storage information.</p>
 *
 * @param id The unique identifier of the storage.
 * @param owner The UUID of the user who owns the storage.
 * @param category The category of the storage.
 * @param width The width of the storage.
 * @param height The height of the storage.
 * @param amount The amount associated with the storage.
 * @param discountAmount The discount amount applied to the storage.
 * @param address The address of the storage, represented by an AddressResponse object.
 *                See {@link com.microservices.warehouse.geos.responses.AddressResponse} for more details.
 * @param flags A response object containing flags related to the storage's status and other attributes.
 *              See {@link com.microservices.warehouse.storages.responses.StorageFlagsResponse} for more details.
 * @param score The score associated with the storage, represented by a Score object.
 *
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
        AddressResponse address,

        /**
         * This field provides control and visibility over the storage's status and other flags.
         * See {@link com.microservices.warehouse.storages.responses.StorageFlagsResponse} for more details.
         */
        StorageFlagsResponse flags,

        /**
         * This field represents the score associated with the storage.
         */
        Score score
) {
}
