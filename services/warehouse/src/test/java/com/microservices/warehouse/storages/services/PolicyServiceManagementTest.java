package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.application.exceptions.IllegalOperationException;
import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.storages.mappers.PolicyMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.PolicyEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
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

class PolicyServiceManagementTest {

    @InjectMocks
    private PolicyServiceManagement underTest;

    @Mock
    private PolicyMapper policyMapper;

    @Mock
    private PolicyJDBCService policyJDBCService;

    @Mock
    private StorageService storageService;

    @Mock
    private StorageMapper storageMapper;

    private final UUID userId = UUID.randomUUID();
    private final Long storageId = 1001L;
    private final String code = "AE124";

    private final PolicyDTO dto = PolicyDTO.builder()
            .code(code)
            .title("title for policy dto")
            .description("we need a simple description, please help me!")
            .build();

    private final PolicyEntity policy = PolicyEntity.builder()
            .code(code)
            .title("title for policy dto")
            .desc("we need a simple description, please help me!")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_insert_policy_successful() {
        // given
        // generate the entities
        PolicyEntity persistedPolicy = generatePersistedPolicy();
        PolicyDTO expectedDto = generatePolicyDto(persistedPolicy);

        // mock
        Mockito.when(policyMapper.toPolicy(dto))
                .thenReturn(policy);
        Mockito.when(policyJDBCService.insertPolicy(policy))
                .thenReturn(persistedPolicy);
        Mockito.when(policyMapper.toDto(persistedPolicy))
                .thenReturn(expectedDto);

        // when
        PolicyDTO result = underTest.insertPolicy(dto);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void should_update_policy_successful() {
        // given
        // generate the entities
        PolicyEntity persistedPolicy = generatePersistedPolicy();
        PolicyEntity updatedPolicy = generatePersistedPolicy();
        PolicyDTO expectedDto = generatePolicyDto(updatedPolicy);

        // mock
        Mockito.when(policyJDBCService.findPolicyByCode(dto.code()))
                .thenReturn(persistedPolicy);
        Mockito.when(policyMapper.toPolicy(dto))
                .thenReturn(policy);
        Mockito.when(policyJDBCService.updatePolicy(persistedPolicy, policy))
                .thenReturn(updatedPolicy);
        Mockito.when(policyMapper.toDto(updatedPolicy))
                .thenReturn(expectedDto);

        // when
        PolicyDTO result = underTest.updatePolicy(dto);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void should_update_storage_policies_by_code_successful() {
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
        StorageResponse result = assertDoesNotThrow(() -> underTest.updateStoragePolicyByCodes(storageId, codes, userId));

        // then
        assertNotNull(result);
        assertEquals(expectedStorage, result);
    }

    @Test
    void should_update_storage_policies_by_code_unsuccessful() {
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
        assertThrows(IllegalOperationException.class, () -> underTest.updateStoragePolicyByCodes(storageId, codes, userId));
    }

    @Test
    void should_delete_policy_by_code_successful() {
        // given
        // generate the entities
        PolicyEntity persistedPolicy = generatePersistedPolicy();
        PolicyDTO expectedDto = generatePolicyDto(persistedPolicy);

        // mock
        Mockito.when(policyJDBCService.findPolicyByCode(code))
                .thenReturn(persistedPolicy);
        Mockito.when(policyMapper.toDto(persistedPolicy))
                .thenReturn(expectedDto);

        // then & when
        // assertion
        PolicyDTO result = assertDoesNotThrow(() -> underTest.deletePolicyByCode(code));
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void check_finding_policy_by_code_successful() {
        // given
        // generate the entities
        PolicyEntity persistedPolicy = generatePersistedPolicy();
        PolicyDTO expectedDto = generatePolicyDto(persistedPolicy);

        // mock
        Mockito.when(policyJDBCService.findPolicyByCode(code))
                .thenReturn(persistedPolicy);
        Mockito.when(policyMapper.toDto(persistedPolicy))
                .thenReturn(expectedDto);

        // when
        PolicyDTO result = underTest.findPolicyByCode(code);

        // then
        // assertion
        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void check_finding_policies_successful() {
        // given
        // generate the entities
        PolicyEntity first = generatePersistedPolicy();
        PolicyEntity second = generatePersistedPolicy();
        PageImpl<PolicyEntity> policies = new PageImpl<>(Arrays.asList(
                first,
                second
        ));

        PageImpl<PolicyDTO> expectedDto = new PageImpl<>(policies.getContent().stream()
                .map(this::generatePolicyDto)
                .collect(Collectors.toList())
        );

        // mock
        Mockito.when(policyJDBCService.findAllPolicies(Pageable.unpaged()))
                .thenReturn(policies);
        Mockito.when(policyMapper.toDto(Mockito.any(PolicyEntity.class))).thenAnswer(invocation -> {
            PolicyEntity policy = invocation.getArgument(0);
            return generatePolicyDto(policy);
        });

        // when
        Page<PolicyDTO> results = underTest.findAllPolicies(Pageable.unpaged());

        // then
        // assertion
        assertNotNull(results);
        assertEquals(expectedDto.getTotalElements(), results.getTotalElements());
        assertEquals(expectedDto.getContent().size(), results.getContent().size());
        assertEquals(expectedDto.getContent(), results.getContent());
    }

    @Test
    void check_finding_all_policies_by_storage_successful() {
        // given
        // generate the entities
        PolicyEntity first = generatePersistedPolicy();
        PolicyEntity second = generatePersistedPolicy();
        List<PolicyEntity> policies = Arrays.asList(
                first,
                second
        );

        List<PolicyDTO> expectedDto = policies.stream()
                .map(this::generatePolicyDto)
                .toList();

        // mock
        Mockito.when(policyJDBCService.findPoliciesByStorageId(storageId))
                .thenReturn(policies);
        Mockito.when(policyMapper.toDto(Mockito.any(PolicyEntity.class))).thenAnswer(invocation -> {
            PolicyEntity policy = invocation.getArgument(0);
            return generatePolicyDto(policy);
        });

        // when
        List<PolicyDTO> results = underTest.findAllPoliciesByStorageId(storageId);

        // then
        // assertion
        assertNotNull(results);
        assertEquals(expectedDto.size(), results.size());
        assertEquals(expectedDto, results);
    }

    private PolicyEntity generatePersistedPolicy() {
        return PolicyEntity.builder()
                .id(12L)
                .code(policy.getCode())
                .title(policy.getTitle())
                .desc(policy.getDesc())
                .build();
    }

    private PolicyDTO generatePolicyDto(PolicyEntity policy) {
        return PolicyDTO.builder()
                .code(policy.getCode())
                .title(policy.getTitle())
                .description(policy.getDesc())
                .build();
    }
}