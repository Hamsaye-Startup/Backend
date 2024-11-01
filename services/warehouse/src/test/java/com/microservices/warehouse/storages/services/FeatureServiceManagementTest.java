package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.IllegalOperationException;
import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.storages.mappers.FeatureMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.*;
import com.microservices.warehouse.storages.responses.StorageFlagsResponse;
import com.microservices.warehouse.storages.responses.StorageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FeatureServiceManagementTest {

    @InjectMocks
    private FeatureServiceManagement underTest;

    @Mock
    private FeatureMapper featureMapper;

    @Mock
    private FeatureJDBCService featureJDBCService;

    @Mock
    private StorageService storageService;

    @Mock
    private StorageMapper storageMapper;

    private final UUID userId = UUID.randomUUID();
    private final Long storageId = 1001L;
    private final String code = "AE124";

    private final FeatureDTO dto = FeatureDTO.builder()
            .code(code)
            .title("title for feature dto")
            .description("we need a simple description, please help me!")
            .build();

    private final FeatureEntity feature = FeatureEntity.builder()
            .code(code)
            .title("title for feature dto")
            .desc("we need a simple description, please help me!")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_insert_feature_successful() {
        // given
        // generate the entities
        FeatureEntity persistedFeature = generatePersistedFeature();
        FeatureDTO expectedDto = generateFeatureDto(persistedFeature);

        // mock
        Mockito.when(featureMapper.toFeature(dto))
                .thenReturn(feature);
        Mockito.when(featureJDBCService.insertFeature(feature))
                .thenReturn(persistedFeature);
        Mockito.when(featureMapper.toDto(persistedFeature))
                .thenReturn(expectedDto);

        // when
        FeatureDTO result = underTest.insertFeature(dto);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void should_update_feature_successful() {
        // given
        // generate the entities
        FeatureEntity persistedFeature = generatePersistedFeature();
        FeatureEntity updatedFeature = generatePersistedFeature();
        FeatureDTO expectedDto = generateFeatureDto(updatedFeature);

        // mock
        Mockito.when(featureJDBCService.findFeatureByCode(dto.code()))
                .thenReturn(persistedFeature);
        Mockito.when(featureMapper.toFeature(dto))
                .thenReturn(feature);
        Mockito.when(featureJDBCService.updateFeature(persistedFeature, feature))
                .thenReturn(updatedFeature);
        Mockito.when(featureMapper.toDto(updatedFeature))
                .thenReturn(expectedDto);

        // when
        FeatureDTO result = underTest.updateFeature(dto);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void should_update_storage_features_by_code_successful() {
        // given
        // declare the inputs
        List<String> codes = Arrays.asList(code, "ET51P", "Q3P56");

        // generate the entities
        StorageEntity storage = StorageEntity.builder()
                .id(storageId).enabled(true).owner(userId)
                .score(Score.builder().votes(0).score(1F).build())
                .width(18).height(32)
                .category(StorageCategoryEnum.BUSINESS)
                .build();

        StorageResponse expectedStorage = StorageResponse.builder()
                .id(storageId).owner(userId)
                .score(Score.builder().votes(0).score(1F).build())
                .width(18).height(32)
                .category("BUSINESS")
                .flags(StorageFlagsResponse.builder()
                        .displayable(true)
                        .build())
                .build();

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(storage);
        Mockito.when(storageMapper.toResponse(storage))
                .thenReturn(expectedStorage);

        // when
        StorageResponse result = assertDoesNotThrow(() -> underTest.updateStorageFeatureByCodes(storageId, codes, userId));

        // then
        assertNotNull(result);
        assertEquals(expectedStorage, result);
    }

    @Test
    void should_update_storage_features_by_code_unsuccessful() {
        // given
        // declare the inputs
        List<String> codes = Arrays.asList(code, "ET51P", "Q3P56");

        // generate the entities
        UUID ownerId = UUID.randomUUID();
        StorageEntity storage = StorageEntity.builder()
                .id(storageId).enabled(true).owner(ownerId)
                .score(Score.builder().votes(0).score(1F).build())
                .width(18).height(32)
                .category(StorageCategoryEnum.BUSINESS)
                .build();

        StorageResponse expectedStorage = StorageResponse.builder()
                .id(storageId).owner(ownerId)
                .score(Score.builder().votes(0).score(1F).build())
                .width(18).height(32)
                .category("BUSINESS")
                .flags(StorageFlagsResponse.builder()
                        .displayable(true)
                        .build())
                .build();

        // mock
        Mockito.when(storageService.findStorageById(storageId))
                .thenReturn(storage);
        Mockito.when(storageMapper.toResponse(storage))
                .thenReturn(expectedStorage);

        // when
        assertThrows(IllegalOperationException.class, () -> underTest.updateStorageFeatureByCodes(storageId, codes, userId));
    }

    @Test
    void should_delete_feature_by_code_successful() {
        // given
        // generate the entities
        FeatureEntity persistedFeature = generatePersistedFeature();
        FeatureDTO expectedDto = generateFeatureDto(persistedFeature);

        // mock
        Mockito.when(featureJDBCService.findFeatureByCode(code))
                .thenReturn(persistedFeature);
        Mockito.when(featureMapper.toDto(persistedFeature))
                .thenReturn(expectedDto);

        // then & when
        // assertion
        FeatureDTO result = assertDoesNotThrow(() -> underTest.deleteFeatureByCode(code));
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void check_finding_feature_by_code_successful() {
        // given
        // generate the entities
        FeatureEntity persistedFeature = generatePersistedFeature();
        FeatureDTO expectedDto = generateFeatureDto(persistedFeature);

        // mock
        Mockito.when(featureJDBCService.findFeatureByCode(code))
                .thenReturn(persistedFeature);
        Mockito.when(featureMapper.toDto(persistedFeature))
                .thenReturn(expectedDto);

        // when
        FeatureDTO result = underTest.findFeatureByCode(code);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void check_finding_features_successful() {
        // given
        // generate the entities
        FeatureEntity first = generatePersistedFeature();
        FeatureEntity second = generatePersistedFeature();
        PageImpl<FeatureEntity> features = new PageImpl<>(Arrays.asList(
                first,
                second
        ));

        PageImpl<FeatureDTO> expectedDto = new PageImpl<>(features.getContent().stream()
                .map(this::generateFeatureDto)
                .collect(Collectors.toList())
        );

        // mock
        Mockito.when(featureJDBCService.findAllFeatures(Pageable.unpaged()))
                .thenReturn(features);
        Mockito.when(featureMapper.toDto(Mockito.any(FeatureEntity.class))).thenAnswer(invocation -> {
            FeatureEntity feature = invocation.getArgument(0);
            return generateFeatureDto(feature);
        });

        // when
        Page<FeatureDTO> results = underTest.findAllFeatures(Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(expectedDto.getTotalElements(), results.getTotalElements());
        assertEquals(expectedDto.getContent().size(), results.getContent().size());
        assertEquals(expectedDto.getContent(), results.getContent());
    }

    @Test
    void check_finding_all_features_by_storage_successful() {
        // given
        // generate the entities
        FeatureEntity first = generatePersistedFeature();
        FeatureEntity second = generatePersistedFeature();
        List<FeatureEntity> features = Arrays.asList(
                first,
                second
        );

        List<FeatureDTO> expectedDto = features.stream()
                .map(this::generateFeatureDto)
                .toList();

        // mock
        Mockito.when(featureJDBCService.findFeaturesByStorageId(storageId))
                .thenReturn(features);
        Mockito.when(featureMapper.toDto(Mockito.any(FeatureEntity.class))).thenAnswer(invocation -> {
            FeatureEntity feature = invocation.getArgument(0);
            return generateFeatureDto(feature);
        });

        // when
        List<FeatureDTO> results = underTest.findAllFeaturesByStorageId(storageId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(expectedDto.size(), results.size());
        assertEquals(expectedDto, results);
    }

    private FeatureEntity generatePersistedFeature() {
        return FeatureEntity.builder()
                .id(12L)
                .code(feature.getCode())
                .title(feature.getTitle())
                .desc(feature.getDesc())
                .build();
    }

    private FeatureDTO generateFeatureDto(FeatureEntity feature) {
        return FeatureDTO.builder()
                .code(feature.getCode())
                .title(feature.getTitle())
                .description(feature.getDesc())
                .build();
    }
}