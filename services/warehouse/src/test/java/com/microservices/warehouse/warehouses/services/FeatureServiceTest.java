package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.repositories.FeatureRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FeatureServiceTest {

    // The test class
    @InjectMocks
    private FeatureService featureService;

    // The dependencies of test class
    @Mock
    private FeatureRepository featureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void success_persist() {
        // given
        FeatureEntity feature = new FeatureEntity(
                null,
                "TEST",
                "This is simple title",
                null,
                null,
                "This is simple description"
        );

        FeatureEntity savedFeature = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description"
        );

        // mock
        Mockito.when(featureRepository.saveAndFlush(feature))
                .thenReturn(savedFeature);

        // when
        FeatureEntity featureEntity = featureService.persist(feature);

        // then
        assertEquals(featureEntity.getCode(), savedFeature.getCode());
        assertEquals(featureEntity.getTitle(), savedFeature.getTitle());
        assertEquals(featureEntity.getDesc(), savedFeature.getDesc());
    }

    @Test
    void success_find_by_code() {
        // given
        String code = "TEST";
        FeatureEntity feature = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description"
        );

        // mock
        Mockito.when(featureRepository.findByCode(code))
                .thenReturn(Optional.of(feature));

        // when
        FeatureEntity featureEntity = featureService.findByCode(code);

        // then
        assertNotNull(featureEntity);
        assertEquals(feature.getCode(), featureEntity.getCode());
        assertEquals(feature.getTitle(), featureEntity.getTitle());
        assertEquals(feature.getDesc(), featureEntity.getDesc());
        verify(featureRepository, times(1)).findByCode(code);
    }

    @Test
    void success_find_all() {
        // given
        List<FeatureEntity> features = new ArrayList<>();
        features.add(new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description"
        ));

        // mock
        Mockito.when(featureRepository.findAll(Pageable.unpaged()))
                .thenReturn(new PageImpl<>(features));

        // when
        Page<FeatureEntity> entityPage = featureService.findAll(Pageable.unpaged());

        // then
        assertEquals(features.size(), entityPage.getTotalElements());
        verify(featureRepository, times(1)).findAll(Pageable.unpaged());
    }
}