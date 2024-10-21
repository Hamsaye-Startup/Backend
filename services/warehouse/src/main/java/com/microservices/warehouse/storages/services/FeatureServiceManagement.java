package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.application.exceptions.IllegalOperationException;
import com.microservices.warehouse.storages.mappers.FeatureMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.FeatureEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class that provides operations related to features, interacting with both feature and storage services.
 * It acts as a facade for managing features and their association with storage entities.
 *
 * <p>This service handles the conversion between data transfer objects (DTOs) and entities,
 * and ensures the correct business logic is applied when interacting with feature and storage services.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FeatureServiceManagement {

    /**
     * Mapper for converting between {@link FeatureDTO} and {@link FeatureEntity}.
     */
    private final FeatureMapper featureMapper;

    /**
     * Service for handling feature operations via JDBC.
     */
    private final FeatureJDBCService featureJDBCService;

    /**
     * Service for handling storage operations.
     */
    private final StorageService storageService;

    /**
     * Mapper for converting between {@link StorageEntity} and {@link StorageResponse}.
     */
    private final StorageMapper storageMapper;

    /**
     * Adds a new feature based on the provided {@link FeatureDTO}.
     *
     * @param dto The {@link FeatureDTO} containing the feature information.
     * @return The created {@link FeatureDTO}.
     * @since 1.0
     */
    public FeatureDTO insertFeature(FeatureDTO dto) {
        // Convert the DTO to entity
        FeatureEntity feature = featureMapper.toFeature(dto);
        return featureMapper.toDto(featureJDBCService.insertFeature(feature));
    }

    /**
     * Updates an existing feature based on the provided {@link FeatureDTO}.
     * Note: This operation changes the feature's code, title, and description.
     *
     * @param dto The {@link FeatureDTO} containing the updated feature information.
     * @return The updated {@link FeatureDTO}.
     * @since 1.0
     */
    public FeatureDTO updateFeature(FeatureDTO dto) {
        // Find the feature by code
        FeatureEntity existed = featureJDBCService.findFeatureByCode(dto.code());

        // Convert the DTO to entity
        FeatureEntity feature = featureMapper.toFeature(dto);
        return featureMapper.toDto(featureJDBCService.updateFeature(existed, feature));
    }

    /**
     * Updates features associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @param featuresCodes The list of feature codes to be updated.
     * @param userId The ID of the current authenticated user.
     * @return The {@link StorageResponse} containing the updated storage information.
     * @throws IllegalOperationException If the user is not authorized to update the storage.
     * @since 1.0
     */
    public StorageResponse updateStorageFeatureByCodes(
            Long storageId,
            List<String> featuresCodes,
            UUID userId
    ) {
        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        // Check the ownership of the storage by userId
        if (!storage.getOwner().equals(userId)) {
            throw new IllegalOperationException(
                    String.format(
                            "User [%s] attempted to access secure information",
                            userId
                    )
            );
        }

        featureJDBCService.updateStorageFeatureByCode(featuresCodes, storageId);
        return storageMapper.toResponse(storage);
    }

    /**
     * Deletes a feature by its code.
     *
     * @param code The code of the feature to be deleted.
     * @return The deleted {@link FeatureDTO}.
     * @since 1.0
     */
    public FeatureDTO deleteFeatureByCode(String code) {
        // Find the feature by code
        FeatureEntity feature = featureJDBCService.findFeatureByCode(code);

        // Delete the feature by code
        featureJDBCService.deleteFeatureByCode(code);
        return featureMapper.toDto(feature);
    }

    /**
     * Finds a feature by its code.
     *
     * @param code The code of the feature to be found.
     * @return The {@link FeatureDTO} corresponding to the feature code.
     * @since 1.0
     */
    public FeatureDTO findFeatureByCode(String code) {
        return featureMapper.toDto(featureJDBCService.findFeatureByCode(code));
    }

    /**
     * Retrieves all features with pagination support.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link FeatureDTO}.
     * @since 1.0
     */
    public Page<FeatureDTO> findAllFeatures(Pageable pageable) {
        return featureJDBCService.findAllFeatures(pageable)
                .map(featureMapper::toDto);
    }

    /**
     * Finds all features associated with a specific storage entity.
     *
     * @param storageId The ID of the storage entity.
     * @return A {@link List} of {@link FeatureDTO} associated with the specified storage.
     * @since 1.0
     */
    public List<FeatureDTO> findAllFeaturesByStorageId(Long storageId) {
        return featureJDBCService.findFeaturesByStorageId(storageId)
                .stream().map(featureMapper::toDto).toList();
    }
}
