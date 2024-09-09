package com.microservices.warehouse.storages.responses;

import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.models.StorageVerifiedEnum;
import lombok.Builder;

/**
 * This record represents a response object containing various flags related to a storage entity.
 * It is used to provide information about the storage's verification, status, and other flags to the client.
 *
 * <p>NOTE: These flags are visible to the client to indicate the storage's attributes like verification and user interactions.</p>
 *
 * @param verified Indicates if the storage is verified. See {@link com.microservices.warehouse.storages.models.StorageVerifiedEnum} for more details.
 * @param status Represents the status of the storage. See {@link com.microservices.warehouse.storages.models.StorageStatusEnum} for more details.
 * @param displayable Indicates if the storage's comments are displayable.
 * @param marked Indicates if the storage has been marked by the user.
 * @param favourite Indicates if the storage has been marked as a favorite by the user.
 *
 * @version 1.0
 */
@Builder
public record StorageFlagsResponse(

        /**
         * This flag is used for displaying safe storages.
         * See {@link com.microservices.warehouse.storages.models.StorageVerifiedEnum}
         * for more details.
         */
        StorageVerifiedEnum verified,

        /**
         * This flag is used for displaying the status of storage.
         * See {@link com.microservices.warehouse.storages.models.StorageStatusEnum}
         * for more details.
         */
        StorageStatusEnum status,

        /**
         * This flag is used for determining if the storage's comments are displayable.
         */
        boolean displayable,

        /**
         * This flag indicates if the storage has been marked by the user.
         */
        boolean marked,

        /**
         * This flag indicates if the storage has been marked as a favorite by the user.
         */
        boolean favourite
) {
}
