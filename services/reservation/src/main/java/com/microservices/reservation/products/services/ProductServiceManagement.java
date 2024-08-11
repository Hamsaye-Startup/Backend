package com.microservices.reservation.products.services;

import com.microservices.reservation.products.mappers.ProductMapper;
import com.microservices.reservation.products.models.ProductEntity;
import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.products.requests.ProductRequest;
import com.microservices.reservation.products.responses.ProductResponse;
import com.microservices.reservation.warehouse.models.ReservationEntity;
import com.microservices.reservation.warehouse.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceManagement {

    private final ProductMapper mapper;
    private final ProductService service;

    private final ProductTypeService productTypeService;
    private final ReservationService reservationService;

    @Transactional(rollbackFor = Exception.class)
    public List<ProductResponse> add(UUID uid, List<ProductRequest> products) {

        // map and persist the product
        List<ProductEntity> productEntities = new ArrayList<>();
        for (ProductRequest product : products) {

            // fetch the type by id
            ProductTypeEntity productType = productTypeService.findTypeById(product.type());

            // fetch the reservation by uid
            ReservationEntity reservation = reservationService.findReservationById(uid);

            ProductEntity productEntity = mapper.toProductEntity(
                    product,
                    productType,
                    reservation
            );
            productEntities.add(service.persist(productEntity));
        }

        return productEntities.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public Page<ProductResponse> findProductsByReservation(UUID uid, Pageable pageable) {
        return service.findProductsByReservation(uid, pageable)
                .map(mapper::toResponse);
    }
}
