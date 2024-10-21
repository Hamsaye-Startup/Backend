package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomJpaPersistanceException;
import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.repositories.FavouritesBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for managing favourite books related to users.
 * It provides CRUD operations for favourite book entities and interacts with the repository layer.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FavouritesBookService {

    /**
     * Repository used for performing CRUD operations on favourite book entities.
     * See {@link com.microservices.warehouse.storages.repositories.FavouritesBookRepository} for more details.
     */
    private final FavouritesBookRepository repository;

    /**
     * Persists a new favourite book entity in the repository.
     *
     * @param favourites The {@link FavouritesBookEntity} to be persisted.
     * @return The persisted {@link FavouritesBookEntity}.
     * @throws CustomJpaPersistanceException if an error occurs during the persistence operation.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public FavouritesBookEntity persist(FavouritesBookEntity favourites) {
        return repository.saveAndFlush(favourites);
    }

    /**
     * Deletes a favourite book entity from the repository.
     *
     * @param favourites The {@link FavouritesBookEntity} to be deleted.
     * @return The deleted {@link FavouritesBookEntity}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public FavouritesBookEntity delete(FavouritesBookEntity favourites) {
        repository.delete(favourites);
        return favourites;
    }

    /**
     * Finds favourite book entities by user ID, with pagination support.
     *
     * @param userId The UUID of the user whose favourite books are to be retrieved.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link FavouritesBookEntity} objects.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<FavouritesBookEntity> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    /**
     * Finds all favourite book entities for a specific user.
     *
     * @param userId The UUID of the user whose favourite books are to be retrieved.
     * @return A {@link List} of {@link FavouritesBookEntity} objects.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FavouritesBookEntity> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Finds a specific favourite book entity by user ID and storage ID.
     *
     * @param userId The UUID of the user.
     * @param storageId The ID of the storage.
     * @return The {@link FavouritesBookEntity} if found, otherwise {@code null}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FavouritesBookEntity findByIdAndStorageId(UUID userId, Long storageId) {
        return repository.findByUserIdAndStorageId(userId, storageId)
                .orElse(null);
    }
}
