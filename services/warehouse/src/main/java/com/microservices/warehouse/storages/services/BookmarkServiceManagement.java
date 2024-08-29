package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkServiceManagement {

    private final BookmarkService service;
    private final StorageService storageService;

    public BookmarkEntity add(Long storageId, UUID userId) {

        // find the storage
        StorageEntity storage = storageService.findStorageById(storageId);

        // generate the Bookmark
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setId(new BookmarkId(userId, storage));
        return service.persist(bookmark);
    }

    public BookmarkEntity delete(Long storageId, UUID userId) {

        // find the bookmark with storage and user
        BookmarkEntity bookmark = service.findByIdAndStorageId(userId, storageId);
        return service.delete(bookmark);
    }

    public Page<BookmarkEntity> findById(UUID userId, Pageable pageable) {
        return service.findByUserId(userId, pageable);
    }
}
