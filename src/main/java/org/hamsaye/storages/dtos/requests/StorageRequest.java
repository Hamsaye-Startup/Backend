package org.hamsaye.storages.dtos.requests;

import org.hamsaye.storages.models.StorageCategoryEntity;

public record StorageRequest(
        String name,
        Integer width,
        Integer height,
        Integer maxWeight,
        Double amount,
        Double discountAmount,
        String description,
        StorageCategoryRequest category
) {}
