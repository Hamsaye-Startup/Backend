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

/**
 * Service class for managing storage operations. This class interacts with various services and performs operations related to storage management,
 * such as inserting, updating, and removing storages. It also handles functionalities related to addresses, reservations, bookmarks, and favorites.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageServiceManagement {

    /**
     * Mapper for converting between storage entities and DTOs.
     * @see com.microservices.warehouse.storages.mappers.StorageMapper
     */
    private final StorageMapper storageMapper;

    /**
     * Service for performing CRUD operations on storage entities.
     * @see com.microservices.warehouse.storages.services.StorageService
     */
    private final StorageService storageService;

    /**
     * Service for handling address operations.
     * @see com.microservices.warehouse.geos.services.AddressService
     */
    private final AddressService addressService;

    /**
     * Service for managing bookmarks.
     * @see com.microservices.warehouse.storages.services.BookmarkService
     */
    private final BookmarkService bookmarkService;

    /**
     * Service for managing favorites.
     * @see com.microservices.warehouse.storages.services.FavouritesBookService
     */
    private final FavouritesBookService favouritesBookService;

    /**
     * Service for handling reservations.
     * @see com.microservices.warehouse.reservations.services.ReservationService
     */
    private final ReservationService reservationService;

    /**
     * Adds a new storage entity.
     * @param storageRequest the storage information provided by the client.
     * @param ownerId the ID of the current authenticated user, who will be the owner of the storage.
     * @return the response containing information about the newly added storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse insertStorage(StorageRequest storageRequest, UUID ownerId) {
        StorageEntity storage = storageMapper.toStorage(storageRequest, ownerId);

        return storageMapper.toResponse(storageService.persist(storage));
    }

    /**
     * Updates the address information of a storage entity.
     * @param id the ID of the storage to be updated.
     * @param addressRequests the new address information provided by the client.
     * @return the response containing information about the updated storage.
     * @throws PersistStorageException if the postal code does not exist.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse updateStorageAddress(Long id, AddressRequests addressRequests) {
        StorageEntity storage = storageService.findStorageById(id);
        AddressEntity address = addressService.generateAddress(addressRequests);
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

    /**
     * Updates the details of a storage entity.
     * @param id the ID of the storage to be updated.
     * @param storageRequest the new storage information provided by the client.
     * @return the response containing information about the updated storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse updateStorageDetails(Long id, StorageRequest storageRequest) {
        StorageEntity storage = storageService.findStorageById(id);
        StorageEntity updated = storageMapper.toStorage(
                storageRequest,
                storage.getOwner(),
                storage.getAddress(),
                storage.getId()
        );

        return storageMapper.toResponse(storageService.updateStorage(updated));
    }

    /**
     * Verifies a storage entity by checking the existence of its postal code.
     * @param id the ID of the storage to be verified.
     * @return the response containing information about the verified storage.
     * @throws PersistStorageException if the postal code does not exist.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse verifyStorageById(Long id) {
        StorageEntity storage = storageService.findStorageById(id);
        if (storage.getAddress().getDetails() == null) {
            throw new PersistStorageException("postal code is not exist");
        }
        storage.setVerified(StorageVerifiedEnum.VERIFIED);

        return storageMapper.toResponse(storageService.updateStorage(storage));
    }

    /**
     * Removes a storage entity if it is not in a guilty status.
     * @param id the ID of the storage to be removed.
     * @param notStatus the status to filter out the storage from being removed.
     * @return the response containing information about the removed storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse removeStorageById(Long id, StorageStatusEnum notStatus) {
        StorageEntity storage = storageService.findStorageById(id);
        storageService.removeStorage(storage, notStatus);

        return storageMapper.toResponse(storage);
    }

    /**
     * Toggles the visibility of a storage entity.
     * @param id the ID of the storage to be updated.
     * @param enabled the flag indicating whether the storage should be enabled or disabled.
     * @return the response containing information about the updated storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageResponse displayStorage(Long id, boolean enabled) {
        StorageEntity storage = storageService.findStorageById(id);
        storage.setEnabled(enabled);

        return storageMapper.toResponse(storageService.updateStorage(storage));
    }

    /**
     * Finds a storage entity by its ID, including whether it is marked or favorited by the current authenticated user.
     * @param id the ID of the storage to be found.
     * @param userId the ID of the current authenticated user.
     * @return the response containing information about the found storage.
     * @since 1.0
     */
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

    /**
     * Finds a storage entity by its ID.
     * @param id the ID of the storage to be found.
     * @return the response containing information about the found storage.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageResponse findStorageById(Long id) {
        StorageEntity storage = storageService.findStorageById(
                id,
                true,
                StorageStatusEnum.ON_BLOCK_STASH
        );

        return storageMapper.toResponse(storage);
    }

    /**
     * Finds all storage entities owned by a specific user.
     * @param userId the ID of the user whose storages are to be found.
     * @param pageable pagination information.
     * @return a page of responses containing information about the user's storages.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageResponse> findStoragesByUserId(UUID userId, Pageable pageable) {
        Page<StorageEntity> storages = storageService.findStoragesByOwner(userId, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);
        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(storages.getContent(), bookmarks, favourites);

        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /**
     * Finds all legal storage entities with filtering options.
     * @param category the storage category to filter by.
     * @param fromDate the start date for reservation filtering.
     * @param toDate the end date for reservation filtering.
     * @param pageable pagination information.
     * @param userId the ID of the current authenticated user.
     * @return a page of responses containing information about the storages.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> findStorages(
            String category,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable,
            UUID userId
    ) {
        StorageCategoryEnum categoryEnum = storageMapper.convertStorageCategory(category);
        Page<StorageEntity> storages = storageService.findStoragesByCategory(
                categoryEnum,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                pageable
        );
        Set<Long> reservedStorageIds = findReservedStorageIdsByCategoryAndReservedTime(
                categoryEnum,
                fromDate,
                toDate
        );
        List<StorageEntity> filteredStorages = storages.stream()
                .filter(storage -> !reservedStorageIds.contains(storage.getId()))
                .collect(Collectors.toList());
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);
        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(filteredStorages, bookmarks, favourites);

        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /**
     * Finds all legal storage entities with filtering options.
     * @param category the storage category to filter by.
     * @param fromDate the start date for reservation filtering.
     * @param toDate the end date for reservation filtering.
     * @param pageable pagination information.
     * @return a page of responses containing information about the storages.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> findStorages(
            String category,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {
        StorageCategoryEnum categoryEnum = storageMapper.convertStorageCategory(category);
        Page<StorageEntity> storages = storageService.findStoragesByCategory(
                categoryEnum,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                pageable
        );
        Set<Long> reservedStorageIds = findReservedStorageIdsByCategoryAndReservedTime(
                categoryEnum,
                fromDate,
                toDate
        );
        List<StorageEntity> filteredStorages = storages.stream()
                .filter(storage -> !reservedStorageIds.contains(storage.getId()))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /**
     * Searches for storage entities by keyword, such as postal code or address.
     * @param value the keyword to search for.
     * @param pageable pagination information.
     * @param userId the ID of the current authenticated user.
     * @return a page of responses containing information about the storages.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> searchStorages(String value, Pageable pageable, UUID userId) {
        Page<StorageEntity> storages = storageService.searchStoragesByValue(value, true, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId);
        List<FavouritesBookEntity> favourites = favouritesBookService.findByUserId(userId);
        List<StorageEntity> checkedStorages = updateStoragesWithUserStatus(storages.getContent(), bookmarks, favourites);

        return new PageImpl<>(checkedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /**
     * Searches for storage entities by keyword, such as postal code or address.
     * @param value the keyword to search for.
     * @param pageable pagination information.
     * @return a page of responses containing information about the storages.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> searchStorages(String value, Pageable pageable) {
        return storageService.searchStoragesByValue(value, true, pageable)
                .map(storageMapper::toResponse);
    }

    /**
     * Finds the IDs of reserved storages based on category and reservation time.
     * @param category the storage category to filter by.
     * @param fromDate the start date for reservation filtering.
     * @param toDate the end date for reservation filtering.
     * @return a set of IDs of reserved storages.
     * @since 1.0
     */
    private Set<Long> findReservedStorageIdsByCategoryAndReservedTime(StorageCategoryEnum category, LocalDate fromDate, LocalDate toDate) {
        List<ReservationEntity> reservations = reservationService.findAllReservationsByCategoryAndReservedTime(category, fromDate, toDate);
        return reservations.stream()
                .map(reservation -> reservation.getStorage().getId())
                .collect(Collectors.toSet());
    }

    /**
     * Updates the storage list with user-specific status (marked or favorite).
     * @param storages the list of storages to be updated.
     * @param bookmarks the list of bookmarks.
     * @param favourites the list of favorites.
     * @return the updated list of storages with user-specific status.
     * @since 1.0
     */
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
