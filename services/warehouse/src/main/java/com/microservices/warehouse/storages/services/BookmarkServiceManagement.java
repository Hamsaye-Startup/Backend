package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This service class acts as a wrapper around the BookmarkService and StorageService,
 * providing additional functionality and interactions specifically for managing bookmarks
 * related to storage entities.
 *
 * <p>NOTE: This class interacts with the BookmarkController to perform operations such as
 * adding, deleting, and finding bookmarks.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class BookmarkServiceManagement {

    /**
     * The service used for performing bookmark operations.
     * See {@link com.microservices.warehouse.storages.services.BookmarkService} for more details.
     */
    private final BookmarkService service;

    /**
     * The service used for performing storage operations.
     * See {@link com.microservices.warehouse.storages.services.StorageService} for more details.
     */
    private final StorageService storageService;

    /**
     * Adds a bookmark for a specific storage by a user.
     *
     * @param storageId The ID of the storage to be bookmarked, provided by the client.
     * @param userId The UUID of the current authenticated user.
     * @return The created BookmarkEntity.
     * @since 1.0
     */
    public BookmarkEntity addBookmark(Long storageId, UUID userId) {

        // Find the storage
        StorageEntity storage = storageService.findStorageById(storageId);

        // Generate the Bookmark
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setId(new BookmarkId(userId, storage));
        return service.persist(bookmark);
    }

    /**
     * Deletes a bookmark for a specific storage by a user.
     *
     * @param storageId The ID of the storage to be unbookmarked, provided by the client.
     * @param userId The UUID of the current authenticated user.
     * @return The deleted BookmarkEntity.
     * @since 1.0
     */
    public BookmarkEntity deleteBookmarkByStorageIdAndUserId(Long storageId, UUID userId) {

        // Find the bookmark with storage and user
        BookmarkEntity bookmark = service.findByIdAndStorageId(userId, storageId);
        return service.delete(bookmark);
    }

    /**
     * Finds bookmarks associated with a specific user, with pagination support.
     *
     * @param userId The UUID of the user whose bookmarks are to be retrieved.
     * @param pageable Pagination information.
     * @return A page of BookmarkEntity objects.
     * @since 1.0
     */
    public Page<BookmarkEntity> findBookmarkByUserId(UUID userId, Pageable pageable) {
        return service.findByUserId(userId, pageable);
    }
}
