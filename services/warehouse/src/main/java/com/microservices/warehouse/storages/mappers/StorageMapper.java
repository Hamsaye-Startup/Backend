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

/**
 * This class is responsible for mapping between the Storage entity, request, and response objects.
 * It handles conversions for creating and updating storage records and mapping entity fields into corresponding response objects.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageMapper {

    /**
     * See {@link com.microservices.warehouse.geos.mappers.AddressMapper}
     * for more details, please.
     */
    private final AddressMapper addressMapper;

    /**
     * Converts a StorageRequest object and owner ID into a StorageEntity.
     * This method creates a new StorageEntity based on the request parameters such as category, dimensions, and amounts.
     *
     * @param storageRequest The request object containing storage details.
     * @param ownerId The ID of the storage's owner (UUID).
     * @return A StorageEntity object representing the new storage record.
     * @since 1.0
     */
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

    /**
     * Converts a StorageRequest object, owner ID, and address entity into a StorageEntity.
     * This method is used for updating an existing storage entity with additional information such as ID and address.
     *
     * @param storageRequest The request object containing updated storage details.
     * @param ownerId The ID of the storage's owner (UUID).
     * @param address The AddressEntity object representing the storage's location.
     * @param id The ID of the storage being updated.
     * @return A StorageEntity object representing the updated storage record.
     * @since 1.0
     */
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

    /**
     * Converts a StorageEntity object into a StorageResponse object.
     * This method provides details such as ID, category, dimensions, amounts, address, and flags for the storage response.
     *
     * @param storage The StorageEntity object representing the storage record in the database.
     * @return A StorageResponse object containing the details of the storage to be sent as a response.
     * @since 1.0
     */
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
                .score(storage.getScore())
                .build();
    }

    /**
     * Converts a category string to the corresponding StorageCategoryEnum value.
     * It throws a ConversionFailedException if the input string does not match a valid category.
     *
     * @param category The category string provided by the client.
     * @return The corresponding StorageCategoryEnum value.
     * @throws ConversionFailedException If the category string is invalid.
     * @since 1.0
     */
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
