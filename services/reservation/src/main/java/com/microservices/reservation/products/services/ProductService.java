package com.microservices.reservation.products.services;

import com.microservices.reservation.products.models.ProductEntity;
import com.microservices.reservation.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductEntity persist(ProductEntity productEntity) {
        return repository.saveAndFlush(productEntity);
    }

    public Page<ProductEntity> findProductsByReservation(UUID uid, Pageable pageable) {
        return repository.findAllByReservation(uid, pageable);
    }
}
