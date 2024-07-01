package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageFeatureRequest;
import org.hamsaye.storages.dtos.responses.StorageFeatureResponse;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageFeatureMapper {

    public StorageFeatureEntity storageFeatureRequestToStorageFeature(StorageFeatureRequest request) {
        return StorageFeatureEntity.builder()
                .title(request.title())
                .icon(request.icon())
                .description(request.description())
                .build();
    }

    public StorageFeatureResponse storageFeatureToStorageFeatureResponse(StorageFeatureEntity feature) {
        return StorageFeatureResponse.builder()
                .title(feature.getTitle())
                .icon(feature.getIcon())
                .description(feature.getDescription())
                .build();
    }
}
