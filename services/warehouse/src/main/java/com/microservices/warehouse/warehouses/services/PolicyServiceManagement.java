package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.PolicyMapper;
import com.microservices.warehouse.warehouses.responses.PolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyServiceManagement {

    private final PolicyService policyService;
    private final PolicyMapper policyMapper;

    public Page<PolicyResponse> findAll(Pageable pageable) {
        return policyService.findAll(pageable)
                .map(policyMapper::toResponse);
    }
}
