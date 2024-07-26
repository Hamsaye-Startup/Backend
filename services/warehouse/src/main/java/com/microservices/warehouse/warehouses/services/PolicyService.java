package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.exceptions.NotFoundPolicyException;
import com.microservices.warehouse.warehouses.models.PolicyEntity;
import com.microservices.warehouse.warehouses.repositories.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PolicyEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundPolicyException(code));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PolicyEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundPolicyException(id.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<PolicyEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
