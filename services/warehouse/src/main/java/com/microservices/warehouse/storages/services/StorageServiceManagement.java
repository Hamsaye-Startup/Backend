package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.requests.AddressRequests;
import com.microservices.warehouse.geos.services.AddressService;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.exceptions.PersistStorageException;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.requests.StorageRequest;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageServiceManagement {

    private final StorageMapper storageMapper;
    private final StorageService storageService;

    private final AddressService addressService;
    private final BookmarkService bookmarkService;
    private final FavouritesBookService favouritesBookService;
    private final ReservationService reservationService;

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse insertStorage(StorageRequest storageRequest, UUID ownerId) {

        // convert the storage request to storage entity
        StorageEntity storage = storageMapper.toStorage(storageRequest, ownerId);
        return storageMapper.toResponse(storageService.persist(storage));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse updateStorageAddress(Long id, AddressRequests addressRequests) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(id);

        // convert the address request to address entity
        AddressEntity address = addressService.generateAddress(addressRequests);

        // if the address is existed, removed and persist a new one
        if (storage.getAddress() != null) {
            boolean removed = addressService.remove(storage.getAddress());
            System.out.printf(
                    "storage address {%s} is deleted : %s%n",
                    storage.getAddress().getUid().toString(),
                    removed
            );
        }

        storage.setAddress(address);
        return storageMapper.toResponse(storageService.updateAddress(storage));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse updateStorageDetails(Long id, StorageRequest storageRequest) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(id);

        // convert the storage request to storage entity
        StorageEntity updated = storageMapper.toStorage(
                storageRequest,
                storage.getOwner(),
                storage.getAddress(),
                storage.getId()
        );

        return storageMapper.toResponse(storageService.updateStorage(updated));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse verifyStorageById(Long id) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(id);

        // check the address detail is existed
        if (storage.getAddress().getDetails() == null) {
            throw new PersistStorageException("postal code is not exist");
        }

        storage.setVerified(StorageVerifiedEnum.VERIFIED);
        return storageMapper.toResponse(storageService.updateStorage(storage));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse removeStorageById(Long id, StorageStatusEnum notStatus) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(id);

        // remove the storage
        storageService.removeStorage(storage, notStatus);
        return storageMapper.toResponse(storage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse displayStorage(Long id, boolean enabled) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(id);

        storage.setEnabled(enabled);
        return storageMapper.toResponse(storageService.updateStorage(storage));
    }

    /*
    * find the legal storage by id
    * This function checks the storage is marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageResponse findStorageById(Long id, UUID userId) {
        StorageEntity storage = storageService.findStorageById(
                id,
                true,
                StorageStatusEnum.ON_BLOCK_STASH
        );

        BookmarkEntity bookmark = bookmarkService.findByIdAndStorageId(userId, id);
        storage.setMarked(bookmark != null);

        FavouritesBookEntity favouritesBook = favouritesBookService.findByIdAndStorageId(userId, id);
        storage.setFavourite(favouritesBook != null);

        return storageMapper.toResponse(storage);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageResponse findStorageById(Long id) {
        StorageEntity storage = storageService.findStorageById(
                id,
                true,
                StorageStatusEnum.ON_BLOCK_STASH
        );
        return storageMapper.toResponse(storage);
    }

    /*
    * find all storages by user id
    * This function checks the storages are marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageResponse> findStoragesByUserId(UUID userId, Pageable pageable) {

        // find the storages and bookmarks and favourites
        Page<StorageEntity> storages = storageService.findStoragesByOwner(userId, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);

        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(storages.getContent(), bookmarks, favourites);
        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /*
    * find the legal storages
    * this function checks the reservation stats by dates and category
    * This function checks the storages are marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> findStorages(
            String category,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable,
            UUID userId
    ) {
        // convert the string to StorageCategoryEnum
        StorageCategoryEnum categoryEnum = storageMapper.convertStorageCategory(category);

        // find the storages by category
        Page<StorageEntity> storages = storageService.findStoragesByCategory(
                categoryEnum,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                pageable
        );

        // find the reserved storages
        Set<Long> reservedStorageIds = findReservedStorageIdsByCategoryAndReservedTime(
                categoryEnum,
                fromDate,
                toDate
        );

        // find the filtered storages and bookmarks
        List<StorageEntity> filteredStorages = storages.stream()
                .filter(storage -> !reservedStorageIds.contains(storage.getId()))
                .collect(Collectors.toList());

        // // find bookmarks and favourites
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);

        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(filteredStorages, bookmarks, favourites);
        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> findStorages(
            String category,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {
        // convert the string to StorageCategoryEnum
        StorageCategoryEnum categoryEnum = storageMapper.convertStorageCategory(category);

        // find the storages by category
        Page<StorageEntity> storages = storageService.findStoragesByCategory(
                categoryEnum,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                pageable
        );

        // find the reserved storages
        Set<Long> reservedStorageIds = findReservedStorageIdsByCategoryAndReservedTime(
                categoryEnum,
                fromDate,
                toDate
        );

        // find the filtered storages and bookmarks
        List<StorageEntity> filteredStorages = storages.stream()
                .filter(storage -> !reservedStorageIds.contains(storage.getId()))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /*
    * find the legal storages by keyword
    * This function checks the storages are marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> searchStorages(String value, Pageable pageable, UUID userId) {

        // find the storages and bookmarks and favourites
        Page<StorageEntity> storages = storageService.searchStoragesByValue(value, true, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId);
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);

        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(storages.getContent(), bookmarks, favourites);
        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> searchStorages(String value, Pageable pageable) {

        // find the storages and bookmarks and favourites
        return storageService.searchStoragesByValue(value, true, pageable)
                .map(storageMapper::toResponse);
    }

    /*
    * find unique reserved storages for finding storages by category and from and to dates
    * */
    private Set<Long> findReservedStorageIdsByCategoryAndReservedTime(StorageCategoryEnum category, LocalDate fromDate, LocalDate toDate) {
        List<ReservationEntity> reservations = reservationService.findAllReservationsByCategoryAndReservedTime(category, fromDate, toDate);
        return reservations.stream()
                .map(reservation -> reservation.getStorage().getId())
                .collect(Collectors.toSet());
    }

    private List<StorageEntity> updateStoragesWithUserStatus(List<StorageEntity> storages, List<BookmarkEntity> bookmarks, List<FavouritesBookEntity> favourites) {
        Map<Long, Boolean> markedMap = bookmarks.stream()
                .collect(Collectors.toMap(b -> b.getId().getStorage().getId(), b -> true));
        Map<Long, Boolean> favouriteMap = favourites.stream()
                .collect(Collectors.toMap(f -> f.getId().getStorage().getId(), f -> true));

        return storages.stream().peek(storage -> {
            storage.setMarked(markedMap.getOrDefault(storage.getId(), false));
            storage.setFavourite(favouriteMap.getOrDefault(storage.getId(), false));
        }).collect(Collectors.toList());
    }
}
