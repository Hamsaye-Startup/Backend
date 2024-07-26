package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.applications.responses.ResponseMessageType;
import com.microservices.warehouse.warehouses.exceptions.NotFoundFeatureException;
import com.microservices.warehouse.warehouses.models.FeatureEntity;
import com.microservices.warehouse.warehouses.repositories.FeatureRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public FeatureEntity persist(FeatureEntity featureEntity) {
        try {
            return repository.saveAndFlush(featureEntity);
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public FeatureEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundFeatureException(code));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FeatureEntity delete(FeatureEntity featureEntity) {
        repository.delete(featureEntity);
        return featureEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<FeatureEntity> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }
}
