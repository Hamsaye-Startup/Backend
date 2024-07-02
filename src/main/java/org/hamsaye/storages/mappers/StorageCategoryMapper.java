package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageCategoryRequest;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.exceptions.StorageCategoryMapperException;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageCategoryMapper {

    public StorageCategoryResponse storageCategoryToStorageCategoryResponse(StorageCategoryEntity category) throws StorageCategoryMapperException {
        try {
            return StorageCategoryResponse.builder()
                    .uid(category.getUid())
                    .code(category.getCode())
                    .name(category.getName())
                    .parent(storageCategoryToStorageCategoryResponse(category.getParent()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageCategoryMapperException();
        }
    }

    public StorageCategoryEntity storageCategoryResponseToStorageCategory(StorageCategoryResponse response) throws StorageCategoryMapperException {
        try {
            return StorageCategoryEntity.builder()
                    .uid(response.uid())
                    .code(response.code())
                    .name(response.name())
                    .parent(storageCategoryResponseToStorageCategory(response.parent()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageCategoryMapperException();
        }
    }

    public StorageCategoryEntity storageCategoryRequestToStorageCategory(StorageCategoryRequest request) throws StorageCategoryMapperException {
        try {
            return StorageCategoryEntity.builder()
                    .code(request.code())
                    .name(request.name())
                    .parent(storageCategoryRequestToStorageCategory(request.parent()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageCategoryMapperException();
        }
    }
}
