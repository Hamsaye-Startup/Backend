package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.PersistBookmarkException;
import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.repositories.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This service class provides operations related to bookmarks, including persisting, deleting, and retrieving bookmarks.
 * It interacts with the BookmarkRepository to perform CRUD operations and handle bookmark entities.
 *
 * <p>NOTE: All methods are transactional and handle various aspects of bookmark management.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class BookmarkService {

    /**
     * The repository used for interacting with the bookmark database.
     * See {@link com.microservices.warehouse.storages.repositories.BookmarkRepository} for more details.
     */
    private final BookmarkRepository repository;

    /**
     * Inserts a new bookmark into the repository.
     *
     * @param bookmark The BookmarkEntity to be persisted.
     * @return The persisted BookmarkEntity.
     * @throws PersistBookmarkException If an exception occurs while persisting the bookmark.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public BookmarkEntity persist(BookmarkEntity bookmark) {
        try {
            return repository.saveAndFlush(bookmark);
        } catch (RuntimeException ex) {
            throw new PersistBookmarkException(ex.getCause(), bookmark.getId().toString());
        }
    }

    /**
     * Deletes a bookmark from the repository.
     *
     * @param bookmark The BookmarkEntity to be deleted.
     * @return The deleted BookmarkEntity.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public BookmarkEntity delete(BookmarkEntity bookmark) {
        repository.delete(bookmark);
        return bookmark;
    }

    /**
     * Finds bookmarks associated with a specific user ID, with pagination support.
     *
     * @param userId The UUID of the user whose bookmarks are to be retrieved.
     * @param pageable Pagination information.
     * @return A page of BookmarkEntity objects.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<BookmarkEntity> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    /**
     * Finds bookmarks associated with a specific user ID without pagination.
     *
     * @param userId The UUID of the user whose bookmarks are to be retrieved.
     * @return A list of BookmarkEntity objects.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<BookmarkEntity> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Finds a bookmark by user ID and storage ID.
     *
     * @param userId The UUID of the user.
     * @param storageId The ID of the storage.
     * @return The BookmarkEntity if found, or null if no matching bookmark exists.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public BookmarkEntity findByIdAndStorageId(UUID userId, Long storageId) {
        return repository.findByUserIdAndStorageId(userId, storageId)
                .orElse(null);
    }
}
