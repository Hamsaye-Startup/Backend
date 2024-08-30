package com.microservices.warehouse.storages.responses;

import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.models.StorageVerifiedEnum;
import lombok.Builder;

@Builder
public record StorageFlagsResponse(
        StorageVerifiedEnum verified,
        StorageStatusEnum status,
        boolean displayable,
        boolean marked,
        boolean favourite
) {
}
