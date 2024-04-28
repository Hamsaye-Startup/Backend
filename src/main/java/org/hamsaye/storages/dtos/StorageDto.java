package org.hamsaye.storages.dtos;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record StorageDto (
        Integer width,
        Integer height,
        Integer maxWeight,
        Double amount,
        Double discountAmount,
        String description
) implements Serializable {
}
