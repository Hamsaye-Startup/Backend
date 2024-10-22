package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.models.keys.BookmarkId;
import com.microservices.warehouse.storages.repositories.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService underTest;

    @Mock
    private BookmarkRepository bookmarkRepository;

    private final UUID userId = UUID.randomUUID();
    private final StorageEntity storage = StorageEntity.builder()
            .id(1001L)
            .desc("This is simple description about the Test Storage")
            .width(12).height(12).owner(userId).amount(1200D).discountAmount(0D)
            .category(StorageCategoryEnum.BUSINESS).enabled(true)
            .score(Score.builder().votes(0).score(0F).build())
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_persist_bookmark_successful() {
        // given
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        BookmarkEntity persistedFavourite = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(bookmarkRepository.save(bookmark)).thenReturn(persistedFavourite);

        // when
        BookmarkEntity result = underTest.persist(bookmark);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(persistedFavourite, result);
    }

    @Test
    void should_delete_bookmark_successful() {
        // given
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .build();

        assertDoesNotThrow(() -> underTest.delete(bookmark));
    }

    @Test
    void check_finding_bookmark_page_by_user_id_successful() {
        // given
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        Page<BookmarkEntity> bookmarks = new PageImpl<>(Collections.singletonList(bookmark));

        // mock
        Mockito.when(bookmarkRepository.findByUserId(userId, Pageable.unpaged()))
                .thenReturn(bookmarks);

        // when
        Page<BookmarkEntity> results = underTest.findByUserId(userId, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(bookmarks.getContent(), results.getContent());
    }

    @Test
    void check_finding_bookmark_list_by_user_id_successful() {
        // given
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        List<BookmarkEntity> bookmarks = Collections.singletonList(bookmark);

        // mock
        Mockito.when(bookmarkRepository.findByUserId(userId))
                .thenReturn(bookmarks);

        // when
        List<BookmarkEntity> results = underTest.findByUserId(userId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(bookmarks, results);
    }

    @Test
    void check_finding_bookmark_by_user_and_storage_successful() {
        // given
        // generate the bookmark
        BookmarkEntity bookmark = BookmarkEntity.builder()
                .id(new BookmarkId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(bookmarkRepository.findByUserIdAndStorageId(userId, storage.getId()))
                .thenReturn(Optional.of(bookmark));

        // when
        BookmarkEntity result = underTest.findByIdAndStorageId(userId, storage.getId());

        // then
        // assertion
        assertNotNull(result);
        assertEquals(bookmark, result);
    }
}