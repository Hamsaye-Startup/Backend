package com.microservices.reservation.products.mappers;

import com.microservices.reservation.products.models.ProductEntity;
import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.products.requests.ProductRequest;
import com.microservices.reservation.products.responses.ProductResponse;
import com.microservices.reservation.warehouse.mappers.ReservationMapper;
import com.microservices.reservation.warehouse.models.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ReservationMapper mapper;

    public ProductEntity toProductEntity(ProductRequest request, ProductTypeEntity type, ReservationEntity reservation) {
        return ProductEntity.builder()
                .type(type)
                .price(request.price())
                .desc(request.desc())
                .reservation(reservation)
                .build();
    }

    public ProductResponse toResponse(ProductEntity product) {
        return ProductResponse.builder()
                .type(product.getType())
                .price(product.getPrice())
                .desc(product.getDesc())
                .reservation(mapper.toResponse(product.getReservation()))
                .build();
    }
}
