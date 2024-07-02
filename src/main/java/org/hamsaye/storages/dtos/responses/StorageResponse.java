package org.hamsaye.storages.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.functional.Functionality;
import org.hamsaye.storages.status.StorageStatus;

import java.util.List;
import java.util.UUID;

@Builder
public record StorageResponse(
        UUID uid,
        String name,
        Integer width,
        Integer height,
        Integer maxWeight,
        Double amount,
        Double discountAmount,
        String description,
        StorageStatus status,
        StorageCategoryResponse category,
        List<StorageFeatureResponse> features

) implements Functionality {}
