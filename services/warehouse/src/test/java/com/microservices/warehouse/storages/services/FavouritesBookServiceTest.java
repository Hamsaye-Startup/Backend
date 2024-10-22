package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import com.microservices.warehouse.storages.repositories.FavouritesBookRepository;
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

class FavouritesBookServiceTest {

    @InjectMocks
    private FavouritesBookService underTest;

    @Mock
    private FavouritesBookRepository favouritesBookRepository;

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
    void should_persist_favourite_successful() {
        // given
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        FavouritesBookEntity persistedFavourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(favouritesBookRepository.save(favourite)).thenReturn(persistedFavourite);

        // when
        FavouritesBookEntity result = underTest.persist(favourite);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(persistedFavourite, result);
    }

    @Test
    void should_delete_favourite_successful() {
        // given
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .build();

        assertDoesNotThrow(() -> underTest.delete(favourite));
    }

    @Test
    void check_finding_favourite_page_by_user_id_successful() {
        // given
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        Page<FavouritesBookEntity> favourites = new PageImpl<>(Collections.singletonList(favourite));

        // mock
        Mockito.when(favouritesBookRepository.findByUserId(userId, Pageable.unpaged()))
                .thenReturn(favourites);

        // when
        Page<FavouritesBookEntity> results = underTest.findByUserId(userId, Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1L, results.getTotalElements());
        assertEquals(1, results.getContent().size());
        assertEquals(favourites.getContent(), results.getContent());
    }

    @Test
    void check_finding_favourite_list_by_user_id_successful() {
        // given
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        List<FavouritesBookEntity> favourites = Collections.singletonList(favourite);

        // mock
        Mockito.when(favouritesBookRepository.findByUserId(userId))
                .thenReturn(favourites);

        // when
        List<FavouritesBookEntity> results = underTest.findByUserId(userId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(favourites, results);
    }

    @Test
    void check_finding_favourite_by_user_and_storage_successful() {
        // given
        // generate the favourite
        FavouritesBookEntity favourite = FavouritesBookEntity.builder()
                .id(new FavouritesId(userId, storage))
                .createdAt(LocalDateTime.now())
                .build();

        // mock
        Mockito.when(favouritesBookRepository.findByUserIdAndStorageId(userId, storage.getId()))
                .thenReturn(Optional.of(favourite));

        // when
        FavouritesBookEntity result = underTest.findByIdAndStorageId(userId, storage.getId());

        // then
        // assertion
        assertNotNull(result);
        assertEquals(favourite, result);
    }
}