package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouritesBookServiceManagement {

    private final FavouritesBookService service;
    private final StorageService storageService;

    public FavouritesBookEntity addFavouritesBook(Long storageId, UUID userId) {

        // find the storage
        StorageEntity storage = storageService.findStorageById(storageId);

        // generate the FavouritesBook
        FavouritesBookEntity favourites = new FavouritesBookEntity();
        favourites.setId(new FavouritesId(userId, storage));
        return service.persist(favourites);
    }

    public FavouritesBookEntity deleteFavouritesBookByStorageIdAndUserId(Long storageId, UUID userId) {

        // find the favourites with storage and user
        FavouritesBookEntity favourites = service.findByIdAndStorageId(userId, storageId);
        return service.delete(favourites);
    }

    public Page<FavouritesBookEntity> findFavouritesBookByUserId(UUID userId, Pageable pageable) {
        return service.findByUserId(userId, pageable);
    }
}
