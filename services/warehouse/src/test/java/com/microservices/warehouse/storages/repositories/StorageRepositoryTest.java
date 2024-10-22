package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageRepositoryTest {

    @Autowired
    private StorageRepository underTest;

    @Autowired
    private EntityManager entityManager;

    private final UUID userId = UUID.randomUUID();
    private final StorageEntity storage = StorageEntity.builder()
            .desc("This is simple description about the Test Storage")
            .width(12).height(12).owner(userId).amount(1200D).discountAmount(0D)
            .category(StorageCategoryEnum.BUSINESS).enabled(true)
            .score(Score.builder().votes(0).score(0F).build())
            .status(StorageStatusEnum.COMPLETELY_SAFE)
            .build();

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findSpecificStorageByIdAndStatusSuccessfully() {
        // persist the entities
        entityManager.persist(storage);

        // find the storage by id and not status (default=not-block)
        StorageEntity specific = underTest.findByIdAndEnabledAndStatusNot(
                storage.getId(),
                true,
                StorageStatusEnum.ON_BLOCK_STASH
        ).orElse(null);

        // assertion
        assertNotNull(specific);
        assertEquals(storage, specific);
    }

    @Test
    void checkNotStatusStorageByIdAndStatusSuccessfully() {
        // persist the entities
        entityManager.persist(storage);

        // check the not status
        StorageEntity empty = underTest.findByIdAndEnabledAndStatusNot(
                storage.getId(),
                true,
                StorageStatusEnum.COMPLETELY_SAFE
        ).orElse(null);

        // assertion
        assertNull(empty);
    }


    @Test
    void findStoragePageByOwnerIdSuccessfully() {
        // persist the entities
        entityManager.persist(storage);

        // find the storage by id
        Page<StorageEntity> storages = underTest.findAllByOwner(userId, Pageable.unpaged());

        // assertion
        assertNotNull(storages);
        assertEquals(1, storages.getTotalElements());
        assertEquals(1, storages.getContent().size());
        assertTrue(storages.getContent().contains(storage));
    }

    @Test
    void checkPostalCodeStoragePageByValueSuccessfully() {
        // generate another storage
        StorageEntity clone = StorageEntity.builder()
                .desc("This is simple description about the Clone Storage")
                .width(12).height(31).owner(userId).amount(4200D).discountAmount(0D)
                .category(StorageCategoryEnum.BUSINESS).enabled(true)
                .score(Score.builder().votes(0).score(0F).build())
                .status(StorageStatusEnum.COMPLETELY_SAFE)
                .build();

        // add address to storages
        storage.setAddress(AddressEntity.builder()
                .details(AddressDetailsEntity.builder()
                        .plaque("12")
                        .postalCode("12344")
                        .build())
                .address("This is simple address for test storage")
                .coordinate("12;43")
                .addressCompat("This is address combat for test storage")
                .build());

        clone.setAddress(AddressEntity.builder()
                .details(AddressDetailsEntity.builder()
                        .plaque("12")
                        .postalCode("43124")
                        .build())
                .address("This is simple address for clone storage")
                .coordinate("12;43")
                .addressCompat("This is address combat for clone storage")
                .build());

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(clone);

        // find the page of storages
        Page<StorageEntity> storages = underTest.searchAllByValue("1234", true, Pageable.unpaged());

        // assertion
        assertNotNull(storages);
        assertEquals(1, storages.getTotalElements());
        assertEquals(1, storages.getContent().size());
        assertTrue(storages.getContent().contains(storage));
    }

    @Test
    void checkAddressStoragePageByValueSuccessfully() {
        // generate another storage
        StorageEntity clone = StorageEntity.builder()
                .desc("This is simple description about the Clone Storage")
                .width(12).height(31).owner(userId).amount(4200D).discountAmount(0D)
                .category(StorageCategoryEnum.BUSINESS).enabled(true)
                .score(Score.builder().votes(0).score(0F).build())
                .status(StorageStatusEnum.COMPLETELY_SAFE)
                .build();

        // add address to storages
        storage.setAddress(AddressEntity.builder()
                .details(AddressDetailsEntity.builder()
                        .plaque("12")
                        .postalCode("12344")
                        .build())
                .address("This is simple address for test storage")
                .coordinate("12;43")
                .addressCompat("This is address combat for test storage")
                .build());

        clone.setAddress(AddressEntity.builder()
                .details(AddressDetailsEntity.builder()
                        .plaque("12")
                        .postalCode("43124")
                        .build())
                .address("This is simple address for clone storage")
                .coordinate("12;43")
                .addressCompat("This is address combat for clone storage")
                .build());

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(clone);

        // find the page of storages
        Page<StorageEntity> storages = underTest.searchAllByValue("clone", true, Pageable.unpaged());

        // assertion
        assertNotNull(storages);
        assertEquals(1, storages.getTotalElements());
        assertEquals(1, storages.getContent().size());
        assertTrue(storages.getContent().contains(clone));
    }

    @Test
    void findStoragePageByCategoryAndEnabledAndStatusSuccessfully() {
        // persist the entities
        entityManager.persist(storage);

        // find the page of storages
        Page<StorageEntity> storages = underTest.findAllByCategoryAndEnabledAndStatusNot(
                StorageCategoryEnum.BUSINESS,
                true,
                StorageStatusEnum.ON_BLOCK_STASH,
                Pageable.unpaged()
        );

        // assertion
        assertNotNull(storages);
        assertEquals(1, storages.getTotalElements());
        assertEquals(1, storages.getContent().size());
        assertTrue(storages.getContent().contains(storage));
    }
}