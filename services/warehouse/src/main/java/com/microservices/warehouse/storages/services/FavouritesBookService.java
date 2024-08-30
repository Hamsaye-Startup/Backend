package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.PersistFavouritesBookException;
import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.repositories.FavouritesBookRepository;
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
public class FavouritesBookService {

    private final FavouritesBookRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public FavouritesBookEntity persist(FavouritesBookEntity favourites) {
        try {
            return repository.saveAndFlush(favourites);
        } catch (RuntimeException ex) {
            throw new PersistFavouritesBookException(ex.getCause(), favourites.getId().toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FavouritesBookEntity delete(FavouritesBookEntity favourites) {
        repository.delete(favourites);
        return favourites;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<FavouritesBookEntity> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<FavouritesBookEntity> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FavouritesBookEntity findByIdAndStorageId(UUID userId, Long storageId) {
        return repository.findByUserIdAndStorageId(userId, storageId)
                .orElse(null);
    }
}
