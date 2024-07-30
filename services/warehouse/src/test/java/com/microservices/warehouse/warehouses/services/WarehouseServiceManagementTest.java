package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.WarehouseMapper;
import com.microservices.warehouse.warehouses.models.CategoryEnum;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.requests.NewWarehouseRequest;
import com.microservices.warehouse.warehouses.requests.WarehouseRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import com.microservices.warehouse.warehouses.responses.LimitedWarehouseResponse;
import com.microservices.warehouse.warehouses.responses.PolicyResponse;
import com.microservices.warehouse.warehouses.responses.WarehouseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class WarehouseServiceManagementTest {

    // The test class
    @InjectMocks
    private WarehouseServiceManagement warehouseServiceManagement;

    // The dependencies of test class
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private WarehouseMapper warehouseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void success_add() {
        // given
        String token = "token";

        Set<String> featuresRequest = new TreeSet<>();
        featuresRequest.add("TEST");

        Set<String> policiesRequest = new TreeSet<>();
        policiesRequest.add("TEST");

        UUID uuid = UUID.randomUUID();
        NewWarehouseRequest newWarehouseRequest = new NewWarehouseRequest(
                uuid,
                CategoryEnum.BUSINESS,
                featuresRequest,
                policiesRequest,
                12,
                12,
                20000.0D,
                15.0D,
                "This is a description for new warehouse"

        );

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

        WarehouseEntity warehouseEntity = new WarehouseEntity(
                null,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity,
                policyEntity,
                null,
                null,
                12,
                12,
                20000.0D,
                15.0D,
                "This is a description for new warehouse",
                null
        );

        WarehouseEntity savedWarehouseEntity = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity,
                policyEntity,
                LocalDateTime.now(),
                null,
                12,
                12,
                20000.0D,
                15.0D,
                "This is a description for new warehouse",
                "TEST"
        );

        Set<FeatureResponse> featureResponses = new HashSet<>();
        featureResponses.add(new FeatureResponse(
                "TEST",
                "This is simple title",
                "This is simple description for feature"
        ));

        Set<PolicyResponse> policyResponses = new HashSet<>();
        policyResponses.add(new PolicyResponse(
                "TEST",
                "This is simple title",
                "This is simple description for policy"
        ));

        WarehouseResponse warehouseResponse = new WarehouseResponse(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureResponses,
                policyResponses,
                12,
                12,
                20000.0D,
                15.0D,
                "This is a description for new warehouse",
                null
        );

        // mock
        Mockito.when(warehouseMapper.toWarehouseEntity(newWarehouseRequest, token))
                .thenReturn(warehouseEntity);

        warehouseEntity.setStatus("TEST");
        Mockito.when(warehouseService.persist(warehouseEntity))
                .thenReturn(savedWarehouseEntity);
        Mockito.when(warehouseMapper.toResponse(savedWarehouseEntity))
                .thenReturn(warehouseResponse);

        // when
        WarehouseResponse response = warehouseServiceManagement.add(newWarehouseRequest, token);

        // then
        assertNotNull(response);
        assertEquals(warehouseResponse, response);
    }

    @Test
    void success_update() {
        // given
        Set<String> featuresRequest = new TreeSet<>();
        featuresRequest.add("TEST2");

        Set<String> policiesRequest = new TreeSet<>();
        policiesRequest.add("TEST2");

        UUID uuid = UUID.randomUUID();
        WarehouseRequest warehouseRequest = new WarehouseRequest(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featuresRequest,
                policiesRequest,
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse2"

        );

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

        WarehouseEntity warehouseEntity = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity,
                policyEntity,
                null,
                null,
                12,
                12,
                20000.0D,
                15.0D,
                "This is a description for new warehouse",
                "TEST"
        );

        Set<FeatureEntity> featureEntity2 = new HashSet<>();
        featureEntity2.add(new FeatureEntity(
                2L,
                "TEST2",
                "This is simple title2",
                LocalDateTime.now(),
                null,
                "This is simple description for feature2"
        ));

        Set<PolicyEntity> policyEntity2 = new HashSet<>();
        policyEntity2.add(new PolicyEntity(
                2L,
                "TEST2",
                "This is simple title2",
                LocalDateTime.now(),
                null,
                "This is simple description for policy2",
                "classpath:policy.txt"
        ));

        WarehouseEntity updateWarehouse = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity2,
                policyEntity2,
                LocalDateTime.now(),
                null,
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse2",
                "TEST"
        );

        WarehouseEntity savedWarehouseEntity = new WarehouseEntity(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureEntity2,
                policyEntity2,
                LocalDateTime.now(),
                LocalDateTime.now(),
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse2",
                "TEST"
        );

        Set<FeatureResponse> featureResponses = new HashSet<>();
        featureResponses.add(new FeatureResponse(
                "TEST2",
                "This is simple title2",
                "This is simple description for feature2"
        ));

        Set<PolicyResponse> policyResponses = new HashSet<>();
        policyResponses.add(new PolicyResponse(
                "TEST2",
                "This is simple title2",
                "This is simple description for policy2"
        ));

        WarehouseResponse warehouseResponse = new WarehouseResponse(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureResponses,
                policyResponses,
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse2",
                null
        );

        // mock
        Mockito.when(warehouseService.findById(warehouseRequest.id()))
                .thenReturn(warehouseEntity);
        Mockito.when(warehouseMapper.toWarehouseEntity(warehouseRequest, warehouseEntity))
                .thenReturn(updateWarehouse);
        Mockito.when(warehouseService.persist(updateWarehouse))
                .thenReturn(savedWarehouseEntity);
        Mockito.when(warehouseMapper.toResponse(savedWarehouseEntity))
                .thenReturn(warehouseResponse);

        // when
        WarehouseResponse response = warehouseServiceManagement.update(warehouseRequest);

        // then
        assertNotNull(response);
        assertEquals(warehouseResponse, response);
    }

    @Test
    void success_find_all_warehouses_without_offset() {
        // given
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

        List<WarehouseEntity> warehouseEntities = new ArrayList<>();
        warehouseEntities.add(new WarehouseEntity(
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
        ));

        List<LimitedWarehouseResponse> limitedWarehouseResponses = new ArrayList<>();
        limitedWarehouseResponses.add(new LimitedWarehouseResponse(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                LocalDateTime.now(),
                12,
                24,
                25000.0D,
                0.0D,
                null
        ));

        // mock
        Mockito.when(warehouseService.findAllWarehouses())
                .thenReturn(warehouseEntities);
        Mockito.when(warehouseMapper.toLimitResponse(any(WarehouseEntity.class)))
                .thenReturn(limitedWarehouseResponses.getFirst());

        // when
        List<LimitedWarehouseResponse> responses = warehouseServiceManagement.findAllWarehouses(null);

        // then
        assertNotNull(responses);
        assertEquals(limitedWarehouseResponses, responses);
    }

    @Test
    void success_find_by_id() {
        // given
        Long id = 1L;

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

        WarehouseEntity warehouseEntity = new WarehouseEntity(
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

        Set<FeatureResponse> featureResponses = new HashSet<>();
        featureResponses.add(new FeatureResponse(
                "TEST",
                "This is simple title",
                "This is simple description for feature"
        ));

        Set<PolicyResponse> policyResponses = new HashSet<>();
        policyResponses.add(new PolicyResponse(
                "TEST",
                "This is simple title",
                "This is simple description for policy"
        ));

        WarehouseResponse warehouseResponse = new WarehouseResponse(
                1L,
                uuid,
                CategoryEnum.BUSINESS,
                featureResponses,
                policyResponses,
                12,
                24,
                25000.0D,
                0.0D,
                "This is a description for new warehouse2",
                null
        );

        // mock
        Mockito.when(warehouseService.findById(id))
                .thenReturn(warehouseEntity);
        Mockito.when(warehouseMapper.toResponse(warehouseEntity))
                .thenReturn(warehouseResponse);

        // when
        WarehouseResponse response = warehouseServiceManagement.findById(id);

        // then
        assertNotNull(response);
        assertEquals(warehouseResponse, response);
    }
}