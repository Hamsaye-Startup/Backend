package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.mappers.FeatureMapper;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.requests.FeatureRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureServiceManagement {

    private final FeatureMapper mapper;
    private final FeatureService featureService;

    public FeatureResponse add(FeatureRequest feature) {
        // generate and persist an entity feature
        FeatureEntity featureEntity = mapper.toFeatureEntity(feature);
        return mapper.toResponse(featureService.persist(featureEntity));
    }

    public FeatureResponse update(FeatureRequest feature) {
        // find the feature by code
        FeatureEntity featureEntity = featureService.findByCode(feature.code());
        FeatureEntity newFeatureEntity = mapper.toFeatureEntity(feature, featureEntity);
        return mapper.toResponse(featureService.persist(newFeatureEntity));
    }

    public FeatureResponse delete(String code) {
        // find the feature by code
        FeatureEntity featureEntity = featureService.findByCode(code);
        return mapper.toResponse(featureService.delete(featureEntity));
    }

    public Page<FeatureResponse> findAllFeatures(Pageable pageable) {
        return featureService.findAll(pageable)
                .map(mapper::toResponse);
    }
}
