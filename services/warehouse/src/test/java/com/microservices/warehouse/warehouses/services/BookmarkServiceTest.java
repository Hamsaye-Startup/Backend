package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.models.*;
import com.microservices.warehouse.warehouses.models.keys.BookmarkId;
import com.microservices.warehouse.warehouses.repositories.BookmarkRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkServiceTest {

    // The test class
    @InjectMocks
    private BookmarkService bookmarkService;

    // The dependencies of test class
    @Mock
    private BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        // given
        UUID uid = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        Set<FeatureEntity> featureEntity = new HashSet<>();
        featureEntity.add(new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description for feature"
        ));

        Set<PolicyEntity> policyEntity = new HashSet<>();
        policyEntity.add(new PolicyEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description for policy",
                "classpath:policy.txt"
        ));

        WarehouseEntity warehouse1 = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity,
                policyEntity,
                LocalDateTime.now(),
                null,
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse",
                "TEST"
        );

        WarehouseEntity warehouse2 = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity,
                policyEntity,
                LocalDateTime.now(),
                null,
                25,
                13,
                20000.0D,
                10.0D,
                "This is a description for new warehouse2",
                "TEST"
        );

        List<BookmarkEntity> bookmarkEntities = List.of(
                new BookmarkEntity(new BookmarkId(uid, warehouse1), LocalDateTime.now()),
                new BookmarkEntity(new BookmarkId(uid, warehouse2), LocalDateTime.now())
        );

        Page<BookmarkEntity> entityPage = new PageImpl<>(bookmarkEntities);

        // mock
        Mockito.when(bookmarkRepository.findById(uid, Pageable.unpaged()))
                .thenReturn(entityPage);

        // when
        Page<BookmarkEntity> response = bookmarkService.findById(uid, Pageable.unpaged());

        // then
        assertNotNull(response);
        assertEquals(entityPage.getTotalElements(), response.getTotalElements());
        assertEquals(entityPage.get().toList(), entityPage.get().toList());
    }
}