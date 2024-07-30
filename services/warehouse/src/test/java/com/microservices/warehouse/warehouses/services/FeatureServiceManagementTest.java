package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.FeatureMapper;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.requests.FeatureRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import lombok.NonNull;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class FeatureServiceManagementTest {

    // The test class
    @InjectMocks
    private FeatureServiceManagement featureServiceManagement;

    // The dependencies of test class
    @Mock
    private FeatureService featureService;
    @Mock
    private FeatureMapper featureMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void success_add() {
        // given
        FeatureRequest featureRequest = new FeatureRequest(
                "TEST",
                "This is simple title",
                "This is simple description"
        );

        FeatureEntity featureEntity = new FeatureEntity(
                null,
                "TEST",
                "This is simple title",
                null,
                null,
                "This is simple description"
        );

        FeatureEntity savedFeatureEntity = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description"
        );
        FeatureResponse featureResponse = new FeatureResponse(
                "TEST",
                "This is simple title",
                "This is simple description"
        );

        // mock
        Mockito.when(featureMapper.toFeatureEntity(featureRequest))
                .thenReturn(featureEntity);
        Mockito.when(featureService.persist(featureEntity))
                .thenReturn(savedFeatureEntity);
        Mockito.when(featureMapper.toResponse(savedFeatureEntity))
                .thenReturn(featureResponse);

        // when
        FeatureResponse response = featureServiceManagement.add(featureRequest);

        // then
        assertEquals(featureResponse.code(), response.code());
        assertEquals(featureResponse.title(), response.title());
        assertEquals(featureResponse.desc(), response.desc());
    }

    @Test
    void success_update() {
        // given
        FeatureRequest featureRequest = new FeatureRequest(
                "TEST",
                "This is simple title2",
                "This is simple description2"
        );

        FeatureEntity savedFeatureEntity = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                null,
                "This is simple description"
        );

        FeatureEntity updateFeatureEntity = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title2",
                LocalDateTime.now(),
                null,
                "This is simple description2"
        );

        FeatureEntity newFeatureEntity = new FeatureEntity(
                1L,
                "TEST",
                "This is simple title2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "This is simple description2"
        );

        FeatureResponse featureResponse = new FeatureResponse(
                "TEST",
                "This is simple title2",
                "This is simple description2"
        );

        // mock
        Mockito.when(featureService.findByCode(featureRequest.code()))
                        .thenReturn(savedFeatureEntity);
        Mockito.when(featureMapper.toFeatureEntity(featureRequest, savedFeatureEntity))
                .thenReturn(updateFeatureEntity);
        Mockito.when(featureService.persist(updateFeatureEntity))
                .thenReturn(newFeatureEntity);
        Mockito.when(featureMapper.toResponse(newFeatureEntity))
                .thenReturn(featureResponse);

        // when
        FeatureResponse response = featureServiceManagement.update(featureRequest);

        // then
        assertEquals(featureResponse.code(), response.code());
        assertEquals(featureResponse.title(), response.title());
        assertEquals(featureResponse.desc(), response.desc());
    }

    @Test
    void success_find_all_features() {
        // given
        List<FeatureEntity> features = new ArrayList<>();
        features.add(new FeatureEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "This is simple description"
        ));

        Page<FeatureEntity> featurePage = new PageImpl<>(features);

        List<FeatureResponse> responses = new ArrayList<>();
        responses.add(new FeatureResponse(
                "TEST",
                "This is simple title",
                "This is simple description"
        ));

        Page<FeatureResponse> responsePage = new PageImpl<>(responses);

        // mock
        Mockito.when(featureService.findAll(Pageable.unpaged()))
                .thenReturn(featurePage);
        Mockito.when(featureMapper.toResponse(any(FeatureEntity.class)))
                .thenReturn(responses.getFirst());

        // when
        Page<FeatureResponse> featureResponses = featureServiceManagement.findAllFeatures(Pageable.unpaged());

        // then
        assertEquals(featureResponses.getTotalElements(), responsePage.getTotalElements());
        assertEquals(featureResponses, responsePage);
    }
}