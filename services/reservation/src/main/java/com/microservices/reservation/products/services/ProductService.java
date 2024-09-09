package com.microservices.reservation.products.services;

import com.microservices.reservation.products.exceptions.PersistProductException;
import com.microservices.reservation.products.models.ProductEntity;
import com.microservices.reservation.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class responsible for managing product-related operations.
 * Handles persistence of products and retrieval of products by reservation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    /**
     * Persists a {@link ProductEntity} into the database.
     * Throws {@link PersistProductException} if an error occurs during persistence.
     *
     * @param productEntity The {@link ProductEntity} to be persisted.
     * @return The persisted {@link ProductEntity}.
     * @throws PersistProductException if an error occurs during persistence.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductEntity persist(ProductEntity productEntity) {
        try {
            return repository.saveAndFlush(productEntity);
        } catch (RuntimeException ex) {
            throw new PersistProductException(
                    ex.getCause(),
                    productEntity.getReservation().getUid().toString()
            );
        }
    }

    /**
     * Retrieves a paginated list of {@link ProductEntity} associated with a specific reservation.
     *
     * @param uid The UUID of the reservation for which to retrieve products.
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link ProductEntity} associated with the specified reservation.
     * @since 1.0
     */
    public Page<ProductEntity> findProductsByReservation(UUID uid, Pageable pageable) {
        return repository.findAllByReservation(uid, pageable);
    }
}
