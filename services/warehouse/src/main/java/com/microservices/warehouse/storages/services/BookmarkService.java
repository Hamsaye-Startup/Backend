package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.NotFoundBookmarkException;
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

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public BookmarkEntity persist(BookmarkEntity bookmark) {
        return repository.saveAndFlush(bookmark);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BookmarkEntity delete(BookmarkEntity bookmark) {
        repository.delete(bookmark);
        return bookmark;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<BookmarkEntity> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<BookmarkEntity> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public BookmarkEntity findByIdAndStorageId(UUID userId, Long storageId) {
        return repository.findByUserIdAndStorageId(userId, storageId)
                .orElse(null);
    }
}
