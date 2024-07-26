package com.microservices.warehouse.warehouses.mappers;

import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.requests.FeatureRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import org.springframework.stereotype.Service;

@Service
public class FeatureMapper {

    public FeatureEntity toFeatureEntity(FeatureRequest request) {
        return FeatureEntity.builder()
                .code(request.code())
                .title(request.title())
                .desc(request.desc())
                .build();
    }

    public FeatureEntity toFeatureEntity(FeatureRequest request, FeatureEntity feature) {
        return FeatureEntity.builder()
                .code(request.code())
                .title(request.title() == null ? feature.getTitle() : request.title())
                .desc(request.desc() == null ? feature.getDesc() : request.desc())
                .build();
    }

    public FeatureResponse toResponse(FeatureEntity feature) {
        return FeatureResponse.builder()
                .code(feature.getCode())
                .title(feature.getTitle())
                .desc(feature.getDesc())
                .build();
    }
}
