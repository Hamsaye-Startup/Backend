package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavouritesBookRepositoryTest {

    @Autowired
    private FavouritesBookRepository underTest;

    @Autowired
    private EntityManager entityManager;

    private final UUID userId = UUID.randomUUID();
    private final StorageEntity storage = StorageEntity.builder()
            .desc("This is simple description about the Test Storage")
            .width(12).height(12).owner(userId).amount(1200D).discountAmount(0D)
            .category(StorageCategoryEnum.BUSINESS).enabled(true)
            .score(Score.builder().votes(0).score(0F).build())
            .build();

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findFavouriteListByUserIdSuccessfully() {
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(favourite);

        // find a list of favourites
        List<FavouritesBookEntity> favourites = underTest.findByUserId(userId);

        // assertion
        assertTrue(favourites.contains(favourite)); // contains the persisted favourite
        assertEquals(1, favourites.size()); // check the size should be 1
    }

    @Test
    void findFavouritePageByUserIdSuccessfully() {
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(favourite);

        // find a Page of favourites
        Page<FavouritesBookEntity> favourites = underTest.findByUserId(userId, Pageable.unpaged());

        // assertion
        assertTrue(favourites.getContent().contains(favourite));
        assertEquals(1, favourites.getTotalElements());
        assertEquals(1, favourites.getContent().size());
    }

    @Test
    void findSpecificFavouriteByUserIdAndStorageIdSuccessfully() {
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(favourite);

        // find a Specific favourite
        FavouritesBookEntity specific = underTest.findByUserIdAndStorageId(userId, storage.getId())
                .orElse(null);

        // assertion
        assertNotNull(specific);
        assertEquals(favourite, specific);
    }

    @Test
    void checkDuplicatePersist() {
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        FavouritesBookEntity clone = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(favourite);

        // check the duplication
        try {
            entityManager.persist(clone);
            fail("duplicate favourite persist in database.");
        } catch (RuntimeException ex) {

            // find a list of favourites
            List<FavouritesBookEntity> favourites = underTest.findByUserId(userId);

            // assertion
            assertTrue(favourites.contains(favourite)); // contains the persisted favourite
            assertEquals(1, favourites.size()); // check the size should be 1
        }
    }
}