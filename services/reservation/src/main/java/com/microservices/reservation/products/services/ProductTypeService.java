package com.microservices.reservation.products.services;

import com.microservices.reservation.products.exceptions.NotFoundProductTypeException;
import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.products.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository repository;

    public ProductTypeEntity findTypeById(Long type) {
        return repository.findById(type)
                .orElseThrow(() -> new NotFoundProductTypeException(type.toString()));
    }
}
