package com.microservices.reservation.products.mappers;

import com.microservices.reservation.products.models.ProductEntity;
import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.products.requests.ProductRequest;
import com.microservices.reservation.products.responses.ProductResponse;
import com.microservices.reservation.warehouse.mappers.ReservationMapper;
import com.microservices.reservation.warehouse.models.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Provides methods to map between {@link ProductRequest}, {@link ProductEntity}, and {@link ProductResponse}.
 * This service is responsible for converting data between the product request and response objects and the corresponding entity.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductMapper {

    /**
     * @see com.microservices.reservation.warehouse.mappers.ReservationMapper
     */
    private final ReservationMapper mapper;

    /**
     * Converts a {@link ProductRequest} into a {@link ProductEntity}.
     *
     * @param request The request containing product details.
     * @param type The type of the product.
     * @param reservation The reservation associated with the product.
     * @return A {@link ProductEntity} populated with the details from the request.
     * @since 1.0
     */
    public ProductEntity toProductEntity(ProductRequest request, ProductTypeEntity type, ReservationEntity reservation) {
        return ProductEntity.builder()
                .type(type)
                .price(request.price())
                .desc(request.desc())
                .reservation(reservation)
                .build();
    }

    /**
     * Converts a {@link ProductEntity} into a {@link ProductResponse}.
     *
     * @param product The product entity to convert.
     * @return A {@link ProductResponse} representing the product.
     * @since 1.0
     */
    public ProductResponse toResponse(ProductEntity product) {
        return ProductResponse.builder()
                .type(product.getType())
                .price(product.getPrice())
                .desc(product.getDesc())
                .reservation(mapper.toResponse(product.getReservation()))
                .build();
    }
}
