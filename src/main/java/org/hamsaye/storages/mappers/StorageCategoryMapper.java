package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageCategoryRequest;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageCategoryMapper {

    public StorageCategoryResponse storageCategoryToStorageCategoryResponse(StorageCategoryEntity category) {
        return StorageCategoryResponse.builder()
                .code(category.getCode())
                .name(category.getName())
                .parent(storageCategoryToStorageCategoryResponse(category.getParent()))
                .build();
    }

    public StorageCategoryEntity storageCategoryRequestToStorageCategory(StorageCategoryRequest request) {
        return StorageCategoryEntity.builder()
                .code(request.code())
                .name(request.name())
                .parent(storageCategoryRequestToStorageCategory(request.parent()))
                .build();
    }
}
