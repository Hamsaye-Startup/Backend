package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
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
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository underTest;

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
    void findBookmarkListByUserIdSuccessfully() {
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(bookmark);

        // find a list of bookmarks
        List<BookmarkEntity> bookmarks = underTest.findByUserId(userId);

        // assertion
        assertTrue(bookmarks.contains(bookmark)); // contains the persisted bookmark
        assertEquals(1, bookmarks.size()); // check the size should be 1
    }

    @Test
    void findBookmarkPageByUserIdSuccessfully() {
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(bookmark);

        // find a Page of bookmarks
        Page<BookmarkEntity> bookmarks = underTest.findByUserId(userId, Pageable.unpaged());

        // assertion
        assertTrue(bookmarks.getContent().contains(bookmark));
        assertEquals(1, bookmarks.getTotalElements());
        assertEquals(1, bookmarks.getContent().size());
    }

    @Test
    void findSpecificBookmarkByUserIdAndStorageIdSuccessfully() {
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(bookmark);

        // find a Specific bookmark
        BookmarkEntity specific = underTest.findByUserIdAndStorageId(userId, storage.getId())
                .orElse(null);

        // assertion
        assertNotNull(specific);
        assertEquals(bookmark, specific);
    }

    @Test
    void checkDuplicatePersist() {
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        BookmarkEntity clone = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(bookmark);

        // check the duplication
        try {
            entityManager.persist(clone);
            fail("duplicate bookmark persist in database.");
        } catch (RuntimeException ex) {

            // find a list of bookmarks
            List<BookmarkEntity> bookmarks = underTest.findByUserId(userId);

            // assertion
            assertTrue(bookmarks.contains(bookmark)); // contains the persisted bookmark
            assertEquals(1, bookmarks.size()); // check the size should be 1
        }
    }
}