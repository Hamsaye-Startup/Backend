package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.storages.models.FeatureEntity;
import com.microservices.warehouse.storages.models.PolicyEntity;
import org.springframework.stereotype.Service;

@Service
public class PolicyMapper {

    public PolicyEntity toPolicy(PolicyDTO dto) {
        return PolicyEntity.builder()
                .code(dto.code())
                .title(dto.title())
                .desc(dto.description())
                .build();
    }

    public PolicyDTO toDto(PolicyEntity policy) {
        return PolicyDTO.builder()
                .code(policy.getCode())
                .title(policy.getTitle())
                .description(policy.getDesc())
                .build();
    }
}
