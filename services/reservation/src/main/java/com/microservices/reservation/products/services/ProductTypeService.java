package com.microservices.reservation.products.services;

import com.microservices.reservation.products.exceptions.NotFoundProductTypeException;
import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.products.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing product type-related operations.
 * Provides functionality to retrieve product types from the repository.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository repository;

    /**
     * Retrieves a {@link ProductTypeEntity} by its ID.
     *
     * @param type The ID of the product type to be retrieved.
     * @return The {@link ProductTypeEntity} associated with the specified ID.
     * @throws NotFoundProductTypeException if the product type with the specified ID is not found.
     * @since 1.0
     */
    public ProductTypeEntity findTypeById(Long type) {
        return repository.findById(type)
                .orElseThrow(() -> new NotFoundProductTypeException(type.toString()));
    }
}
