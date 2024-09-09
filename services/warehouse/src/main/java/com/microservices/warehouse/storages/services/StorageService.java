package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.geos.exceptions.PersistAddressException;
import com.microservices.warehouse.storages.exceptions.NotFoundStorageException;
import com.microservices.warehouse.storages.exceptions.PersistStorageException;
import com.microservices.warehouse.storages.exceptions.StorageIsNotRemovableException;
import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.repositories.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class for managing storage entities. Provides operations for creating, updating, removing,
 * and querying storage entities. Interacts with the {@link StorageRepository} for persistence and
 * query operations.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageService {

    /**
     * Repository for performing CRUD operations on storage entities.
     */
    private final StorageRepository storageRepository;

    /**
     * Persists a new storage entity. Sets default values for fields before saving.
     *
     * @param storage The {@link StorageEntity} to be persisted.
     * @return The persisted {@link StorageEntity}.
     * @throws PersistStorageException If an error occurs while persisting the storage entity.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity persist(StorageEntity storage) {
        try {
            storage.setEnabled(true);
            storage.setVerified(StorageVerifiedEnum.NOT_VERIFIED);
            storage.setStatus(StorageStatusEnum.COMPLETELY_SAFE);
            storage.setScore(
                    Score.builder()
                            .score(0f)
                            .votes(0)
                            .build()
            );
            return storageRepository.save(storage);
        }
        catch (RuntimeException ex) {
            throw new PersistStorageException(ex.getCause(), storage.getOwner().toString());
        }
    }

    /**
     * Updates the address of an existing storage entity.
     *
     * @param storage The {@link StorageEntity} with updated address information.
     * @return The updated {@link StorageEntity}.
     * @throws PersistAddressException If an error occurs while updating the address.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity updateAddress(StorageEntity storage) {
        try {
            return storageRepository.save(storage);
        } catch (RuntimeException ex) {
            throw new PersistAddressException(ex.getCause(), storage.getId().toString());
        }
    }

    /**
     * Updates an existing storage entity.
     *
     * @param storage The {@link StorageEntity} with updated information.
     * @return The updated {@link StorageEntity}.
     * @throws PersistStorageException If an error occurs while updating the storage entity.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity updateStorage(StorageEntity storage) {
        try {
            return storageRepository.save(storage);
        } catch (RuntimeException ex) {
            throw new PersistStorageException(ex.getCause(), storage.getId().toString());
        }
    }

    /**
     * Removes a storage entity if its status is not equal to the specified status.
     *
     * @param storage The {@link StorageEntity} to be removed.
     * @param notStatus The status that prevents removal.
     * @throws StorageIsNotRemovableException If the storage entity's status matches the specified status.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeStorage(StorageEntity storage, StorageStatusEnum notStatus) {
        if (storage.getStatus().equals(notStatus)) {
            throw new StorageIsNotRemovableException(
                    String.format("Cannot remove storageId[%s] with %s status", storage.getId().toString(), notStatus)
            );
        }
        storageRepository.delete(storage);
    }

    /**
     * Removes a storage entity without any restrictions.
     *
     * @param storage The {@link StorageEntity} to be removed.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeStorage(StorageEntity storage) {
        storageRepository.delete(storage);
    }

    /**
     * Finds a storage entity by its ID.
     *
     * @param id The ID of the storage entity.
     * @return The {@link StorageEntity} with the specified ID.
     * @throws NotFoundStorageException If no storage entity with the specified ID is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageEntity findStorageById(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(() -> new NotFoundStorageException(id.toString()));
    }

    /**
     * Finds a storage entity by its ID with additional filtering options.
     *
     * @param id The ID of the storage entity.
     * @param enabled Flag indicating whether the storage is enabled.
     * @param notStatus The status to exclude from results.
     * @return The {@link StorageEntity} matching the criteria.
     * @throws NotFoundStorageException If no storage entity matching the criteria is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageEntity findStorageById(Long id, boolean enabled, StorageStatusEnum notStatus) {
        return storageRepository.findByIdAndEnabledAndStatusNot(id, enabled, notStatus)
                .orElseThrow(() -> new NotFoundStorageException(id.toString()));
    }

    /**
     * Finds all storage entities owned by a specific user.
     *
     * @param owner The ID of the owner.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link StorageEntity} owned by the specified user.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> findStoragesByOwner(UUID owner, Pageable pageable) {
        return storageRepository.findAllByOwner(owner, pageable);
    }

    /**
     * Searches for storage entities based on a keyword and filtering options.
     *
     * @param value The keyword for filtering on postal code and address.
     * @param enabled Flag indicating whether the storage is enabled.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link StorageEntity} matching the search criteria.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> searchStoragesByValue(String value, boolean enabled, Pageable pageable) {
        return storageRepository.searchAllByValue(value, enabled, pageable);
    }

    /**
     * Finds storage entities by category and filtering options.
     *
     * @param category The category of the storage.
     * @param enabled Flag indicating whether the storage is enabled.
     * @param notStatus The status to exclude from results.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link StorageEntity} matching the criteria.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> findStoragesByCategory(
            StorageCategoryEnum category,
            boolean enabled,
            StorageStatusEnum notStatus,
            Pageable pageable
    ) {
        return storageRepository.findAllByCategoryAndEnabledAndStatusNot(category, enabled, notStatus, pageable);
    }
}
