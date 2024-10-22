package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.repositories.CommentRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CommentServiceTest {

    @InjectMocks
    private CommentService underTest;

    @Mock
    private CommentRepository commentRepository;

    private final UUID[] userId = {UUID.randomUUID(), UUID.randomUUID()};
    private final StorageEntity storage = StorageEntity.builder()
            .desc("This is simple description about the Test Storage")
            .width(12).height(12).owner(userId[0]).amount(1200D).discountAmount(0D)
            .category(StorageCategoryEnum.BUSINESS).enabled(true)
            .score(Score.builder().votes(0).score(0F).build())
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_insert_comment_successful() {
        // given
        // generate a sample comment
        CommentEntity comment = CommentEntity.builder()
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for test comment")
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        CommentEntity persistedComment = CommentEntity.builder()
                .id(1001L)
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for test comment")
                .storage(storage)
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(commentRepository.save(comment)).thenReturn(persistedComment);

        // when
        // persist the comment
        CommentEntity result = underTest.insertComment(comment, storage);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(persistedComment, result);
    }

    @Test
    void should_insert_comment_unsuccessful() {
        // given
        // generate a sample comment
        CommentEntity comment = CommentEntity.builder()
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for fail comment")
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(commentRepository.save(comment)).thenThrow(RuntimeException.class);

        // then & when
        // assertion
        assertThrows(RuntimeException.class, () -> underTest.insertComment(comment, storage));
    }

    @Test
    void should_delete_comment_successful() {
        // given
        // generate a sample comment
        CommentEntity comment = CommentEntity.builder()
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for fail comment")
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        // then & when
        // assertion
        assertDoesNotThrow(() -> underTest.deleteComment(comment));
    }

    @Test
    void check_finding_comment_by_id_successful() {
        // given
        // generate a sample comment
        CommentEntity comment = CommentEntity.builder()
                .id(1001L)
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for test comment")
                .enabled(false)
                .commentAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(commentRepository.findById(1001L))
                .thenReturn(Optional.of(comment));

        // when
        CommentEntity result = underTest.findCommentById(1001L);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(comment, result);
        verify(commentRepository, times(1)).findById(1001L);
    }

    @Test
    void check_finding_comment_page_by_storage_successful() {
        // given
        // generate a sample comment
        CommentEntity first = CommentEntity.builder()
                .id(1001L)
                .score(2F)
                .commentBy(userId[0])
                .content("This is simple content for first comment")
                .enabled(false)
                .storage(storage)
                .commentAt(LocalDateTime.now())
                .build();

        CommentEntity second = CommentEntity.builder()
                .id(1002L)
                .score(2F)
                .commentBy(userId[1])
                .content("This is simple content for second comment")
                .enabled(false)
                .storage(storage)
                .commentAt(LocalDateTime.now())
                .build();

        PageImpl<CommentEntity> comments = new PageImpl<>(Arrays.asList(first, second));

        // mock
        Mockito.when(commentRepository.findAllByStorage(storage, Pageable.unpaged()))
                .thenReturn(comments);

        // when
        Page<CommentEntity> results = underTest.findAllCommentsByStorage(storage, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(2L, results.getTotalElements());
        assertEquals(2, results.getContent().size());
        assertEquals(comments.getContent(), results.getContent());
    }

    @Test
    void check_finding_legal_comment_page_by_storage_successful() {
        // given
        // generate a sample comment
        CommentEntity comment = CommentEntity.builder()
                .id(1002L)
                .score(2F)
                .commentBy(userId[1])
                .content("This is simple content for second comment")
                .enabled(true)
                .storage(storage)
                .commentAt(LocalDateTime.now())
                .build();

        PageImpl<CommentEntity> comments = new PageImpl<>(Collections.singletonList(comment));

        // mock
        Mockito.when(commentRepository.findAllByStorageAndEnabled(storage, true, Pageable.unpaged()))
                .thenReturn(comments);

        // when
        Page<CommentEntity> results = underTest.findAllCommentsByStorage(storage, true, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(comments.getContent(), results.getContent());
    }
}