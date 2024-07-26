package com.microservices.warehouse.warehouses.mappers;

import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.responses.PolicyResponse;
import org.springframework.stereotype.Service;

@Service
public class PolicyMapper {

    public PolicyResponse toResponse(PolicyEntity policy) {
        return PolicyResponse.builder()
                .code(policy.getCode())
                .title(policy.getTitle())
                .desc(policy.getDesc())
                .build();
    }
}
