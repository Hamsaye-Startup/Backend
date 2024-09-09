package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class responsible for managing favourite books related to users.
 * It provides methods for adding, deleting, and finding favourite books,
 * and interacts with both the FavouritesBookService and StorageService.
 *
 * <p>NOTE: This class interacts with the FavouriteBook Controller.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FavouritesBookServiceManagement {

    /**
     * The service used for performing operations related to favourite books.
     * See {@link com.microservices.warehouse.storages.services.FavouritesBookService} for more details.
     */
    private final FavouritesBookService service;

    /**
     * The service used for performing operations related to storage entities.
     * See {@link com.microservices.warehouse.storages.services.StorageService} for more details.
     */
    private final StorageService storageService;

    /**
     * Adds a book to the user's list of favourites.
     *
     * @param storageId The ID of the storage entity to be favourited, provided by the client.
     * @param userId The UUID of the current authenticated user.
     * @return The created {@link FavouritesBookEntity}.
     * @since 1.0
     */
    public FavouritesBookEntity addFavouritesBook(Long storageId, UUID userId) {

        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        // Create and save the FavouritesBook entity
        FavouritesBookEntity favourites = new FavouritesBookEntity();
        favourites.setId(new FavouritesId(userId, storage));
        return service.persist(favourites);
    }

    /**
     * Removes a book from the user's list of favourites.
     *
     * @param storageId The ID of the storage entity to be unfavourited, provided by the client.
     * @param userId The UUID of the current authenticated user.
     * @return The deleted {@link FavouritesBookEntity}.
     * @since 1.0
     */
    public FavouritesBookEntity deleteFavouritesBookByStorageIdAndUserId(Long storageId, UUID userId) {

        // Find the FavouritesBook entity by user ID and storage ID
        FavouritesBookEntity favourites = service.findByIdAndStorageId(userId, storageId);
        return service.delete(favourites);
    }

    /**
     * Retrieves all favourite books for a specific user, with pagination support.
     *
     * @param userId The UUID of the user whose favourite books are to be retrieved.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link FavouritesBookEntity} objects.
     * @since 1.0
     */
    public Page<FavouritesBookEntity> findFavouritesBookByUserId(UUID userId, Pageable pageable) {
        return service.findByUserId(userId, pageable);
    }
}
