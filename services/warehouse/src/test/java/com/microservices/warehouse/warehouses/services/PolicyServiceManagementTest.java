package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.PolicyMapper;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import com.microservices.warehouse.warehouses.responses.PolicyResponse;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PolicyServiceManagementTest {

    // The test class
    @InjectMocks
    private PolicyServiceManagement policyServiceManagement;

    // The dependencies of test class
    @Mock
    private PolicyService policyService;
    @Mock
    private PolicyMapper policyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void success_find_all() {
        // given
        List<PolicyEntity> policies = new ArrayList<>();
        policies.add(new PolicyEntity(
                1L,
                "TEST",
                "This is simple title",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "This is simple description",
                "classpath:policy.txt"
        ));

        Page<PolicyEntity> policyPage = new PageImpl<>(policies);

        List<PolicyResponse> responses = new ArrayList<>();
        responses.add(new PolicyResponse(
                "TEST",
                "This is simple title",
                "This is simple description"
        ));

        Page<PolicyResponse> responsePage = new PageImpl<>(responses);

        // mock
        Mockito.when(policyService.findAll(Pageable.unpaged()))
                .thenReturn(policyPage);
        Mockito.when(policyMapper.toResponse(any(PolicyEntity.class)))
                .thenReturn(responses.getFirst());

        // when
        Page<PolicyResponse> policyResponses = policyServiceManagement.findAll(Pageable.unpaged());

        // then
        assertEquals(policyResponses.getTotalElements(), responsePage.getTotalElements());
        assertEquals(policyResponses, responsePage);
    }
}