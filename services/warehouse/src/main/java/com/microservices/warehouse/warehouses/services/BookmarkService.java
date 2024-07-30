package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.exceptions.NotFoundBookmarkException;
import com.microservices.warehouse.warehouses.models.BookmarkEntity;
import com.microservices.warehouse.warehouses.repositories.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<BookmarkEntity> findById(UUID uid, Pageable pageable) {
        return repository.findById(uid, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public BookmarkEntity findByIdAndWarehouse(UUID userId, Long warehouseId) {
        return repository.findByIdAndWarehouseId(userId, warehouseId)
                .orElseThrow(() -> new NotFoundBookmarkException(userId.toString() + ", " + warehouseId));
    }
}
