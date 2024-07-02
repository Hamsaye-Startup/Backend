package org.hamsaye.storages.mappers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageRequest;
import org.hamsaye.storages.dtos.responses.StorageResponse;
import org.hamsaye.storages.exceptions.StorageMapperException;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.status.StorageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageMapper {

    private StorageCategoryMapper categoryMapper;
    private StorageFeatureMapper featureMapper;

    @Autowired
    public StorageMapper(StorageCategoryMapper categoryMapper, StorageFeatureMapper featureMapper) {
        this.categoryMapper = categoryMapper;
        this.featureMapper = featureMapper;
    }

    public StorageResponse storageToStorageResponse(StorageEntity storage) throws StorageMapperException {
        try {
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
                    .features(storage.getFeatures()
                            .stream()
                            .map(featureMapper::storageFeatureToStorageFeatureResponse)
                            .collect(Collectors.toList()))
                    .category(categoryMapper.storageCategoryToStorageCategoryResponse(storage.getCategory()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageMapperException();
        }
    }

    public StorageEntity storageResponseToStorage(StorageResponse response) throws StorageMapperException {
        try {
            return StorageEntity.builder()
                    .name(response.name())
                    .width(response.width())
                    .height(response.height())
                    .maxWeight(response.maxWeight())
                    .amount(response.amount())
                    .discountAmount(response.discountAmount())
                    .description(response.description())
                    .features(response.features()
                            .stream()
                            .map(featureMapper::storageFeatureResponseToStorageFeature)
                            .collect(Collectors.toList()))
                    .category(categoryMapper.storageCategoryResponseToStorageCategory(response.category()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageMapperException();
        }
    }

    public StorageEntity storageRequestToStorage(StorageRequest request) throws StorageMapperException {
        try {
            return StorageEntity.builder()
                    .name(request.name())
                    .width(request.width())
                    .height(request.height())
                    .maxWeight(request.maxWeight())
                    .amount(request.amount())
                    .discountAmount(request.discountAmount())
                    .description(request.description())
                    .features(request.features()
                            .stream()
                            .map(featureMapper::storageFeatureResponseToStorageFeature)
                            .collect(Collectors.toList()))
                    .category(categoryMapper.storageCategoryResponseToStorageCategory(request.category()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageMapperException();
        }
    }

    public StorageStatus stringToStorageStatus(String string) throws StorageMapperException {
        try {
            char[] status = string.toCharArray();
            return new StorageStatus(
                    status[0] == '1',
                    status[1] == '1'
            );
        } catch (RuntimeException ex) {
            throw new StorageMapperException();
        }
    }

    public String storageStatusToString(StorageStatus status) throws StorageMapperException {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(status.VERIFIED() ? '1' : '0');
            builder.append(status.REGISTERED() ? '1' : '0');
            return builder.toString();
        } catch (RuntimeException ex) {
            throw new StorageMapperException();
        }
    }
}
