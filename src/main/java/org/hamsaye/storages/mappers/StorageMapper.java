package org.hamsaye.storages.mappers;

import org.hamsaye.storages.dtos.requests.StorageRequest;
import org.hamsaye.storages.dtos.responses.StorageResponse;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.status.StorageStatus;
import org.springframework.stereotype.Service;

@Service
public class StorageMapper {

    public StorageResponse storageToStorageResponse(StorageEntity storage) {
        return StorageResponse.builder()
                .uid(storage.getUid())
                .name(storage.getName())
                .width(storage.getWidth())
                .height(storage.getHeight())
                .maxWeight(storage.getMaxWeight())
                .amount(storage.getAmount())
                .discountAmount(storage.getDiscountAmount())
                .description(storage.getDescription())
                .status(stringToStorageStatus(storage.getStatus()))
                .build();
    }

    public StorageEntity storageRequestToStorage(StorageRequest request) {
        return StorageEntity.builder()
                .name(request.name())
                .width(request.width())
                .height(request.height())
                .maxWeight(request.maxWeight())
                .amount(request.amount())
                .discountAmount(request.discountAmount())
                .description(request.description())
                .build();
    }

/*    private StorageCategoryEntity storageRequestToStorageCategory(StorageRequest request) {
        return StorageCategoryEntity.builder()
                .code(request.category().code())
                .name(request.category().name())
                .parent(storageCategoryRequestToStorageCategory(request.category().parent()))
                .build();
    }

    private StorageCategoryEntity storageCategoryRequestToStorageCategory(StorageCategoryRequest request) {
        return StorageCategoryEntity.builder()
                .code(request.code())
                .name(request.name())
                .parent(storageCategoryRequestToStorageCategory(request.parent()))
                .build();
    }*/

    public StorageStatus stringToStorageStatus(String string) {
        char[] status = string.toCharArray();
        return new StorageStatus(
                status[0] == '1',
                status[1] == '1'
        );
    }

    public String storageStatusToString(StorageStatus status) {
        StringBuilder builder = new StringBuilder();
        builder.append(status.VERIFIED() ? '1' : '0');
        builder.append(status.REGISTERED() ? '1' : '0');
        return builder.toString();
    }
}
