package com.microservices.warehouse.storages.responses;

import com.microservices.warehouse.geos.responses.AddressResponse;
import com.microservices.warehouse.storages.models.Score;
import lombok.Builder;

import java.util.UUID;

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
        StorageFlagsResponse flags,
        Score score
) {
}
