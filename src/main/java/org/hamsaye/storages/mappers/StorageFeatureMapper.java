package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageFeatureRequest;
import org.hamsaye.storages.dtos.responses.StorageFeatureResponse;
import org.hamsaye.storages.exceptions.StorageFeatureMapperException;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageFeatureMapper {

    public StorageFeatureEntity storageFeatureRequestToStorageFeature(StorageFeatureRequest request) throws StorageFeatureMapperException {
        try {
            return StorageFeatureEntity.builder()
                    .title(request.title())
                    .icon(request.icon())
                    .description(request.description())
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageFeatureMapperException();
        }
    }

    public StorageFeatureEntity storageFeatureResponseToStorageFeature(StorageFeatureResponse response) throws StorageFeatureMapperException {
        try {
            return StorageFeatureEntity.builder()
                    .uid(response.uid())
                    .title(response.title())
                    .icon(response.icon())
                    .description(response.description())
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageFeatureMapperException();
        }
    }

    public StorageFeatureResponse storageFeatureToStorageFeatureResponse(StorageFeatureEntity feature) throws StorageFeatureMapperException {
        try {
            return StorageFeatureResponse.builder()
                    .uid(feature.getUid())
                    .title(feature.getTitle())
                    .icon(feature.getIcon())
                    .description(feature.getDescription())
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageFeatureMapperException();
        }
    }
}
