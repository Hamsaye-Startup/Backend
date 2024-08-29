package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.geos.exceptions.PersistAddressException;
import com.microservices.warehouse.storages.exceptions.NotFoundStorageException;
import com.microservices.warehouse.storages.exceptions.PersistStorageException;
import com.microservices.warehouse.storages.exceptions.StorageIsNotRemovableException;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.models.StorageVerifiedEnum;
import com.microservices.warehouse.storages.repositories.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    /*
    * new storages is not verified
    * */
    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity persist(StorageEntity storage) {
        try {
            storage.setEnabled(true);
            storage.setVerified(StorageVerifiedEnum.NOT_VERIFIED);
            storage.setStatus(StorageStatusEnum.COMPLETELY_SAFE);
            return storageRepository.save(storage);
        }
        catch (RuntimeException ex) {
            throw new PersistStorageException(ex.getCause(), storage.getOwner().toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity updateAddress(StorageEntity storage) {
        try {
            return storageRepository.save(storage);
        } catch (RuntimeException ex) {
            throw new PersistAddressException(ex.getCause(), storage.getId().toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StorageEntity updateStorage(StorageEntity storage) {
        try {
            return storageRepository.save(storage);
        } catch (RuntimeException ex) {
            throw new PersistStorageException(ex.getCause(), storage.getId().toString());
        }
    }

    /*
    * remove storages by host(owner) can be accept when the status of storage
    * doesn't equal to ON_BLOCK_STASH(notStatus value)
    * */
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeStorage(StorageEntity storage, StorageStatusEnum notStatus) {
        if (storage.getStatus().equals(notStatus)) {
            throw new StorageIsNotRemovableException(
                    String.format(
                            "can not remove storageId[%s] with %s status",
                            storage.getId().toString(),
                            notStatus
                    )
            );
        }
        storageRepository.delete(storage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeStorage(StorageEntity storage) {
        storageRepository.delete(storage);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageEntity findStorageById(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(() -> new NotFoundStorageException(id.toString()));
    }

    /*
    * find storages that check the status flag doesn't equal notStatus value
    * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageEntity findStorageById(Long id, StorageStatusEnum notStatus) {
        return storageRepository.findByIdAndStatusNot(id, notStatus)
                .orElseThrow(() -> new NotFoundStorageException(id.toString()));
    }

    /*
     * find displayable storages that check the status flag doesn't equal notStatus value
     * */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public StorageEntity findStorageById(Long id, boolean enabled, StorageStatusEnum notStatus) {
        return storageRepository.findByIdAndEnabledAndStatusNot(id, enabled, notStatus)
                .orElseThrow(() -> new NotFoundStorageException(id.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> findStoragesByOwner(UUID owner, Pageable pageable) {
        return storageRepository.findAllByOwner(owner, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> searchStoragesByValue(String value, boolean enabled, Pageable pageable) {
        return storageRepository.searchAllByValue(value, enabled, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<StorageEntity> findStoragesByCategory(
            StorageCategoryEnum category,
            boolean enabled,
            StorageStatusEnum status,
            Pageable pageable
    ) {
        return storageRepository.findAllByCategoryAndEnabledAndStatusNot(category, enabled, status, pageable);
    }
}
