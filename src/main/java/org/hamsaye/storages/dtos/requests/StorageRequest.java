package org.hamsaye.storages.dtos.requests;

import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.dtos.responses.StorageFeatureResponse;

import java.util.List;

public record StorageRequest(
        String name,
        Integer width,
        Integer height,
        Integer maxWeight,
        Double amount,
        Double discountAmount,
        String description,
        StorageCategoryResponse category,
        List<StorageFeatureResponse> features
) {}
