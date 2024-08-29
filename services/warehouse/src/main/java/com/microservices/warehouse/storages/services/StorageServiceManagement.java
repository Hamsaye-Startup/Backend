package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.requests.AddressRequests;
import com.microservices.warehouse.geos.services.AddressService;
import com.microservices.warehouse.reservations.models.ReservationEntity;
import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.exceptions.InternalErrorException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageServiceManagement {

    private final StorageMapper storageMapper;
    private final StorageService storageService;

    private final AddressService addressService;
    private final BookmarkService bookmarkService;
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

        // TODO: implement liked logic
        return storageMapper.toResponse(storage);
    }

    /*
    * find all storages by user id
    * This function checks the storages are marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageResponse> findStoragesByUserId(UUID userId, Pageable pageable) {

        // find the storages and bookmarks
        Page<StorageEntity> storages = storageService.findStoragesByOwner(userId, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc

        // set the mark value based on the current userId
        List<StorageEntity> markedStorages = calcMarkedStorages(
                mergeSort(storages.toList()), // sort the storages desc
                bookmarks
        );

        // TODO: implement same logic for liked
        return new PageImpl<>(markedStorages, pageable, storages.getTotalElements())
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
        Set<StorageEntity> reservedStorages = findReservedStoragesByCategoryAndReservedTime(
                categoryEnum,
                fromDate,
                toDate
        );

        // find the filtered storages and bookmarks
        List<StorageEntity> filteredStorages = filterReservedStorages(
                mergeSort(storages.toList()),
                reservedStorages.stream().toList()
        );
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId); // order by desc

        // set the mark value based on the current userId
        List<StorageEntity> markedStorages = calcMarkedStorages(
                filteredStorages,
                bookmarks
        );

        // TODO: implement same logic for liked
        return new PageImpl<>(markedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /*
    * find the legal storages by keyword
    * This function checks the storages are marked or liked by current user
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Page<StorageResponse> searchStorages(String value, Pageable pageable, UUID userId) {

        // find the storages and bookmarks
        Page<StorageEntity> storages = storageService.searchStoragesByValue(value, true, pageable);
        List<BookmarkEntity> bookmarks = bookmarkService.findByUserId(userId);

        // set the mark value based on the current userId
        List<StorageEntity> markedStorages = calcMarkedStorages(
                mergeSort(storages.toList()), // sort the storages desc
                bookmarks
        );

        // TODO: implement same logic for liked
        return new PageImpl<>(markedStorages, pageable, storages.getTotalElements())
                .map(storageMapper::toResponse);
    }

    /*
    * find unique reserved storages for finding storages by category and from and to dates
    * */
    private Set<StorageEntity> findReservedStoragesByCategoryAndReservedTime(
            StorageCategoryEnum category,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        // filter the reserved storages
        List<ReservationEntity> reservations = reservationService.findAllReservationsByCategoryAndReservedTime(
                category,
                fromDate,
                toDate
        );
        return reservations.stream()
                .map(reservationEntity -> reservationEntity.getReservedStorage().getStorage())
                .collect(Collectors.toSet());
    }

    /*
    * The storages need to filter when some of the reserved on particular time -> it is good for searching
    * */
    private List<StorageEntity> filterReservedStorages(List<StorageEntity> sortedStorages, List<StorageEntity> reservedStorages) {

        // Pointers for reservedStorages and sortedStorages
        int reservedPointer = 0, storagePointer = 0;

        // Pointer for writing to array2
        int writeIndex = 0;

        while (storagePointer < sortedStorages.size()) {

            if (reservedPointer >= reservedStorages.size() || !reservedStorages.get(reservedPointer)
                    .getId().equals(sortedStorages.get(storagePointer).getId())) {
                sortedStorages.set(writeIndex, sortedStorages.get(storagePointer));
                writeIndex++;
            }
            storagePointer++;

            if (reservedPointer < reservedStorages.size() &&
                    storagePointer >= sortedStorages.size() || reservedStorages.get(reservedPointer)
                    .getId().equals(sortedStorages.get(storagePointer).getId())
            ) {
                reservedPointer++;
            }
        }

        // Resize array2 to remove trailing elements
        while (sortedStorages.size() > writeIndex) {
            sortedStorages.removeLast();
        }
        return sortedStorages;
    }

    /*
    * The storages and bookmarks should sort before occurring this method
    * */
    private List<StorageEntity> calcMarkedStorages(List<StorageEntity> sortedStorages, List<BookmarkEntity> sortedBookmarks) {

        // define the temp variables
        int storagePointer = 0;
        int bookmarkPointer = 0;

        while (storagePointer < sortedStorages.size() && bookmarkPointer < sortedBookmarks.size()) {

            // compare the storage bookmark createdAt is after than storage createdAt
            if (sortedBookmarks.get(bookmarkPointer).getCreatedAt()
                    .isAfter(sortedStorages.get(storagePointer).getCreatedAt())) {

                sortedStorages.get(storagePointer).setMarked(false);
                storagePointer += 1;
            }
            else {
                // compare the sortedStorages exist in list and bookmarks
                if (sortedBookmarks.get(bookmarkPointer).getId().getStorage()
                        .equals(sortedStorages.get(storagePointer))) {

                    sortedStorages.get(storagePointer).setMarked(true);
                    bookmarkPointer += 1;
                    storagePointer += 1;
                }
                else {
                    bookmarkPointer += 1;
                }
            }
        }

        // The remaining warehouses are not marked
        while (storagePointer < sortedStorages.size()) {
            sortedStorages.get(storagePointer).setMarked(false);
            storagePointer += 1;
        }

        return sortedStorages;
    }

    private <T> List<T> mergeSort(List<T> data) {

        // check the size
        if (data.size() <= 1) {
            return data;
        }

        // split the list into two halves
        int middle = data.size() / 2;
        List<T> left = new ArrayList<>(data.subList(0, middle));
        List<T> right = new ArrayList<>(data.subList(middle, data.size()));

        // sort both halves
        mergeSort(left);
        mergeSort(right);

        // merge the sorted halves back together
        merge(data, left, right);

        return data;
    }

    private <T> void merge(List<T> data, List<T> left, List<T> right) {

        // define the indexes
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            LocalDateTime leftCreatedAt = getCreatedAt(left.get(i));
            LocalDateTime rightCreatedAt = getCreatedAt(right.get(j));

            // compare the LocalDateTime values and sort in descending order
            if (leftCreatedAt.isAfter(rightCreatedAt) || leftCreatedAt.equals(rightCreatedAt)) {
                data.set(k++, left.get(i++));
            } else {
                data.set(k++, right.get(j++));
            }
        }

        // copy remaining elements of left
        while (i < left.size()) {
            data.set(k++, left.get(i++));
        }

        // copy remaining elements of right
        while (j < right.size()) {
            data.set(k++, right.get(j++));
        }
    }

    private <T> LocalDateTime getCreatedAt(T obj) {
        try {
            return (LocalDateTime) obj.getClass().getMethod("getCreatedAt").invoke(obj);
        } catch (Exception ex) {
            throw new InternalErrorException("method named getCreatedAt not found");
        }
    }
}
