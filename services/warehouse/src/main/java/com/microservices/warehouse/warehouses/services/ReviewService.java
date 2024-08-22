package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.exceptions.NotFoundReviewException;
import com.microservices.warehouse.warehouses.models.ReviewEntity;
import com.microservices.warehouse.warehouses.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public ReviewEntity persist(ReviewEntity review) {
        return repository.saveAndFlush(review);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ReviewEntity findByWarehouseId(Long warehouseId) {
        return repository.findByWarehouseId(warehouseId)
                .orElseThrow(() -> new NotFoundReviewException(warehouseId.toString()));
    }
}
