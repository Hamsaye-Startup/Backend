package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.CustomNotFoundException;
import com.microservices.warehouse.application.exceptions.CustomNotRemovableObjectException;
import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.repositories.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageServiceTest {

    @InjectMocks
    private StorageService underTest;

    @Mock
    private StorageRepository storageRepository;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private StorageEntity generateStorage(UUID owner, String desc, String address, String postalCode) {
        return StorageEntity.builder()
                .owner(owner)
                .amount(2000D)
                .width(12)
                .height(12)
                .discountAmount(0D)
                .category(StorageCategoryEnum.BUSINESS)
                .address(AddressEntity.builder()
                        .address(address)
                        .addressCompat("This is simple address")
                        .coordinate("14;12")
                        .details(AddressDetailsEntity.builder().postalCode(postalCode).build())
                        .build())
                .score(Score.builder().score(0F).votes(0).build())
                .desc(desc)
                .status(StorageStatusEnum.COMPLETELY_SAFE)
                .enabled(true)
                .build();
    }

    @Test
    void should_persist_successful() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                userId,
                "This is sample description for test storage",
                "This is simple address for test storage",
                "12"
        );

        StorageEntity persistedStorage = generateStorage(
                userId,
                "This is sample description for test storage",
                "This is simple address for test storage",
                "12"
        );
        persistedStorage.setId(1001L);

        // mock
        Mockito.when(storageRepository.save(storage)).thenReturn(persistedStorage);

        // when
        // persist the storage
        StorageEntity result = underTest.persist(storage);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(persistedStorage, result);
    }

    @Test
    void should_persist_unsuccessful() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for fail storage",
                "This is simple address for fail storage",
                "123"
        );

        // mock
        Mockito.when(storageRepository.save(storage)).thenThrow(RuntimeException.class);

        // when & then
        // persist the storage
        assertThrows(RuntimeException.class, () -> underTest.persist(storage));
    }

    @Test
    void should_remove_successful() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                userId,
                "This is sample description for test storage",
                "This is simple address for test storage",
                "12"
        );
        storage.setId(1001L);

        // when & then
        // persist the storage
        assertDoesNotThrow(() -> underTest.removeStorage(storage, StorageStatusEnum.ON_BLOCK_STASH));
    }

    @Test
    void should_remove_unsuccessful_throws_exception() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for fail storage",
                "This is simple address for fail storage",
                "123"
        );
        storage.setId(1001L);

        // when & then
        // persist the storage
        assertThrows(CustomNotRemovableObjectException.class, () -> underTest.removeStorage(storage, StorageStatusEnum.COMPLETELY_SAFE));
    }

    @Test
    void check_finding_storage_by_id_successful() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for test storage",
                "This is simple address for test storage",
                "123"
        );
        storage.setId(1001L);

        // mock
        Mockito.when(storageRepository.findById(storage.getId())).thenReturn(Optional.of(storage));

        // when
        StorageEntity result = underTest.findStorageById(storage.getId());

        // then
        // assertion
        assertNotNull(result);
        assertEquals(storage, result);
    }

    @Test
    void check_finding_storage_by_id_unsuccessful_throws_exception() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for fail storage",
                "This is simple address for fail storage",
                "123"
        );
        storage.setId(1001L);

        // mock
        Mockito.when(storageRepository.findById(storage.getId())).thenThrow(CustomNotFoundException.class);

        // when & then
        // persist the storage
        assertThrows(CustomNotFoundException.class, () -> underTest.findStorageById(storage.getId()));
    }

    @Test
    void check_finding_legal_storage_by_id_successful() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for test storage",
                "This is simple address for test storage",
                "123"
        );
        storage.setId(1001L);

        // mock
        Mockito.when(storageRepository.findByIdAndEnabledAndStatusNot(storage.getId(), true, StorageStatusEnum.ON_BLOCK_STASH))
                .thenReturn(Optional.of(storage));

        // when
        StorageEntity result = underTest.findStorageById(storage.getId(), true, StorageStatusEnum.ON_BLOCK_STASH);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(storage, result);
    }

    @Test
    void check_finding_legal_storage_by_id_unsuccessful_throws_exception() {
        // given
        // generate a sample storage
        StorageEntity storage = generateStorage(
                null,
                "This is sample description for fail storage",
                "This is simple address for fail storage",
                "123"
        );
        storage.setId(1001L);

        // mock
        Mockito.when(storageRepository.findByIdAndEnabledAndStatusNot(storage.getId(), true, storage.getStatus()))
                .thenThrow(CustomNotFoundException.class);

        // when & then
        // persist the storage
        assertThrows(CustomNotFoundException.class, () -> underTest.findStorageById(storage.getId(), true, storage.getStatus()));
    }

    @Test
    void check_finding_storage_page_by_owner_id_successful() {
        // given
        // generate a sample storage
        StorageEntity first = generateStorage(
                userId,
                "This is sample description for first storage",
                "This is simple address for first storage",
                "12"
        );

        StorageEntity second = generateStorage(
                userId,
                "This is sample description for second storage",
                "This is simple address for second storage",
                "121"
        );

        PageImpl<StorageEntity> storages = new PageImpl<>(Arrays.asList(first, second));

        // mock
        Mockito.when(storageRepository.findAllByOwner(userId, Pageable.unpaged()))
                .thenReturn(storages);

        // when
        Page<StorageEntity> results = underTest.findStoragesByOwner(userId, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(storages.getTotalElements(), results.getTotalElements());
        assertEquals(storages.getContent().size(), results.getContent().size());
        assertEquals(storages, results);
    }

    @Test
    void check_searching_storage_page_by_postal_code_successful() {
        // given
        // generate a sample storage
        StorageEntity first = generateStorage(
                userId,
                "This is sample description for first storage",
                "This is simple address for first storage",
                "12"
        );

        StorageEntity second = generateStorage(
                userId,
                "This is sample description for second storage",
                "This is simple address for second storage",
                "121"
        );

        PageImpl<StorageEntity> storages = new PageImpl<>(Arrays.asList(first, second));

        // mock
        Mockito.when(storageRepository.searchAllByValue("12", true, Pageable.unpaged()))
                .thenReturn(storages);

        // when
        Page<StorageEntity> results = underTest.searchStoragesByValue("12", true, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(storages.getTotalElements(), results.getTotalElements());
        assertEquals(storages.getContent().size(), results.getContent().size());
        assertEquals(storages, results);
    }

    @Test
    void check_searching_storage_page_by_address_successful() {
        // given
        // generate a sample storage
        StorageEntity first = generateStorage(
                userId,
                "This is sample description for first storage",
                "This is simple address for first storage",
                "12"
        );

        StorageEntity second = generateStorage(
                userId,
                "This is sample description for second storage",
                "This is simple address for second storage",
                "121"
        );

        PageImpl<StorageEntity> storages = new PageImpl<>(Collections.singletonList(first));

        // mock
        Mockito.when(storageRepository.searchAllByValue("first", true, Pageable.unpaged()))
                .thenReturn(storages);

        // when
        Page<StorageEntity> results = underTest.searchStoragesByValue("first", true, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(storages.getTotalElements(), results.getTotalElements());
        assertEquals(storages.getContent().size(), results.getContent().size());
        assertEquals(storages, results);
    }

    @Test
    void check_finding_storage_page_by_category_successful() {
        // given
        // generate a sample storage
        StorageEntity first = generateStorage(
                userId,
                "This is sample description for first storage",
                "This is simple address for first storage",
                "12"
        );

        StorageEntity second = generateStorage(
                UUID.randomUUID(),
                "This is sample description for second storage",
                "This is simple address for second storage",
                "121"
        );

        PageImpl<StorageEntity> storages = new PageImpl<>(Arrays.asList(first, second));

        // mock
        Mockito.when(storageRepository.findAllByCategoryAndEnabledAndStatusNot(
                StorageCategoryEnum.BUSINESS,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                Pageable.unpaged()
        )).thenReturn(storages);

        // when
        Page<StorageEntity> results = underTest.findStoragesByCategory(
                StorageCategoryEnum.BUSINESS,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                Pageable.unpaged()
        );

        // then
        // assertion
        assertNotNull(results);
        assertEquals(storages.getTotalElements(), results.getTotalElements());
        assertEquals(storages.getContent().size(), results.getContent().size());
        assertEquals(storages, results);
    }
}