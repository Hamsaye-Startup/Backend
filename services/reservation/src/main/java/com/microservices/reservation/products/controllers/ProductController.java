package com.microservices.reservation.products.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.products.requests.ProductRequest;
import com.microservices.reservation.products.responses.ProductResponse;
import com.microservices.reservation.products.services.ProductServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Handles HTTP requests related to products within reservations.
 * Provides endpoints for adding products to a reservation and retrieving products by reservation ID.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final MessageMapper mapper;
    private final ProductServiceManagement management;

    /**
     * Adds a list of products to a reservation identified by the given ID.
     *
     * @param uid The UUID of the reservation to which the products will be added.
     * @param products The list of products to be added.
     * @return A {@link ResponseEntity} containing the response data for the added products.
     * @since 1.0
     */
    @PostMapping("/reservation/{id}")
    public ResponseEntity<?> add(
            @PathVariable("id") UUID uid,
            @RequestBody List<ProductRequest> products
    ) {
        List<ProductResponse> responses = management.add(uid, products);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Retrieves a paginated list of products associated with a reservation identified by the given ID.
     *
     * @param uid The UUID of the reservation for which to retrieve products.
     * @param pageable The pagination information.
     * @return A {@link ResponseEntity} containing the paginated list of products.
     * @since 1.0
     */
    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> showProductsByReservation(
            @PathVariable("id") UUID uid,
            Pageable pageable
    ) {
        Page<ProductResponse> responses = management.findProductsByReservation(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
