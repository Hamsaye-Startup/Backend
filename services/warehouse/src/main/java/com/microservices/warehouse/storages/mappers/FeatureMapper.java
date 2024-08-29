package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.storages.models.FeatureEntity;
import org.springframework.stereotype.Service;

@Service
public class FeatureMapper {

    public FeatureEntity toFeature(FeatureDTO dto) {
        return FeatureEntity.builder()
                .code(dto.code())
                .title(dto.title())
                .desc(dto.description())
                .build();
    }

    public FeatureDTO toDto(FeatureEntity feature) {
        return FeatureDTO.builder()
                .code(feature.getCode())
                .title(feature.getTitle())
                .description(feature.getDesc())
                .build();
    }
}
