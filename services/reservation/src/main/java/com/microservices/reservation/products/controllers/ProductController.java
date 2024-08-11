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

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final MessageMapper mapper;
    private final ProductServiceManagement management;

    @PostMapping("/reservation/{id}")
    public ResponseEntity<?> add(
            @PathVariable("id") UUID uid,
            @RequestBody List<ProductRequest> products
    ) {
        List<ProductResponse> responses = management.add(uid, products);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<?> showProductsByReservation(
            @PathVariable("id") UUID uid,
            Pageable pageable
    ) {
        Page<ProductResponse> responses = management.findProductsByReservation(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
