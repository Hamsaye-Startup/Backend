package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.models.BookmarkEntity;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.models.keys.BookmarkId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkServiceManagement {

    private final BookmarkService service;
    private final WarehouseService warehouseService;

    public BookmarkEntity add(Long warehouseId, UUID userId) {
        // find the warehouse
        WarehouseEntity warehouse = warehouseService.findById(warehouseId);

        // generate the Bookmark
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setId(new BookmarkId(userId, warehouse));
        return service.persist(bookmark);
    }

    public BookmarkEntity delete(Long warehouseId, UUID userId) {
        // find the bookmark with warehouse and user
        BookmarkEntity bookmark = service.findByIdAndWarehouse(userId, warehouseId);
        return service.delete(bookmark);
    }

    public Page<BookmarkEntity> findById(UUID userId, Pageable pageable) {
        return service.findById(userId, pageable);
    }
}
