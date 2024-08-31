package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.geos.mappers.AddressMapper;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.requests.StorageRequest;
import com.microservices.warehouse.storages.responses.StorageFlagsResponse;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageMapper {

    private final AddressMapper addressMapper;

    public StorageEntity toStorage(StorageRequest storageRequest, UUID ownerId) {
        return StorageEntity.builder()
                .owner(ownerId)
                .category(convertStorageCategory(storageRequest.category()))
                .width(storageRequest.width())
                .height(storageRequest.height())
                .amount(storageRequest.amount())
                .discountAmount(storageRequest.discountAmount())
                .desc(storageRequest.desc())
                .build();
    }

    public StorageEntity toStorage(StorageRequest storageRequest, UUID ownerId, AddressEntity address, Long id) {
        return StorageEntity.builder()
                .id(id)
                .owner(ownerId)
                .category(convertStorageCategory(storageRequest.category()))
                .width(storageRequest.width())
                .height(storageRequest.height())
                .amount(storageRequest.amount())
                .discountAmount(storageRequest.discountAmount())
                .address(address)
                .desc(storageRequest.desc())
                .build();
    }

    public StorageResponse toResponse(StorageEntity storage) {
        return StorageResponse.builder()
                .id(storage.getId())
                .owner(storage.getOwner())
                .category(storage.getCategory().getName())
                .width(storage.getWidth())
                .height(storage.getHeight())
                .amount(storage.getAmount())
                .discountAmount(storage.getDiscountAmount())
                .address(storage.getAddress() == null ? null :
                        addressMapper.toResponse(storage.getAddress()))
                .flags(StorageFlagsResponse.builder()
                        .verified(storage.getVerified())
                        .status(storage.getStatus())
                        .displayable(storage.isEnabled())
                        .marked(storage.isMarked())
                        .favourite(storage.isFavourite())
                        .build())
                .build();
    }

    public StorageCategoryEnum convertStorageCategory(String category) {
        try {
            return StorageCategoryEnum.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException ex) {

            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(StorageCategoryEnum.class),
                    category,
                    ex.getCause()
            );
        }
    }


}
