package com.microservices.warehouse.storages.repositories;

import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test", "dev"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository underTest;

    @Autowired
    private EntityManager entityManager;

    private final UUID[] userId = {UUID.randomUUID(), UUID.randomUUID()};
    private final StorageEntity storage = StorageEntity.builder()
            .desc("This is simple description about the Test Storage")
            .width(12).height(12).owner(userId[0]).amount(1200D).discountAmount(0D)
            .category(StorageCategoryEnum.BUSINESS).enabled(true)
            .score(Score.builder().votes(0).score(0F).build())
            .build();

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void findAllCommentPageByStorageSuccessfully() {
        // generate the comment
        CommentEntity firstComment = CommentEntity.builder()
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for first comment")
                .storage(storage)
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        CommentEntity secondComment = CommentEntity.builder()
                .score(5F)
                .commentBy(userId[1])
                .content("This is simple content for second comment")
                .storage(storage)
                .enabled(true)
                .commentAt(LocalDateTime.now())
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(firstComment);
        entityManager.persist(secondComment);

        // find the list of comments
        Page<CommentEntity> comments = underTest.findAllByStorage(storage, Pageable.unpaged());

        // assertion
        assertNotNull(comments);
        assertEquals(2, comments.getTotalElements());
        assertEquals(2, comments.getContent().size());
        assertTrue(comments.getContent().contains(firstComment));
        assertTrue(comments.getContent().contains(secondComment));
    }

    @Test
    void findEnabledCommentPageByStorageSuccessfully() {
        // generate the comment
        CommentEntity firstComment = CommentEntity.builder()
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for first comment")
                .storage(storage)
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        CommentEntity secondComment = CommentEntity.builder()
                .score(5F)
                .commentBy(userId[1])
                .content("This is simple content for second comment")
                .storage(storage)
                .enabled(true)
                .commentAt(LocalDateTime.now())
                .build();

        // persist the entities
        entityManager.persist(storage);
        entityManager.persist(firstComment);
        entityManager.persist(secondComment);

        // find the list of comments
        Page<CommentEntity> comments = underTest.findAllByStorageAndEnabled(storage, true, Pageable.unpaged());

        // assertion
        assertNotNull(comments);
        assertEquals(1, comments.getTotalElements());
        assertEquals(1, comments.getContent().size());
        assertTrue(comments.getContent().contains(secondComment));
    }
}