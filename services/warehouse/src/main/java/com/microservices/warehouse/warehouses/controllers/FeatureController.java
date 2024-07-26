package com.microservices.warehouse.warehouses.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.warehouses.requests.FeatureRequest;
import com.microservices.warehouse.warehouses.responses.FeatureResponse;
import com.microservices.warehouse.warehouses.services.FeatureServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final MessageMapper mapper;
    private final FeatureServiceManagement management;

    @PostMapping
    public ResponseEntity<?> add(FeatureRequest feature) {
        FeatureResponse response = management.add(feature);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping
    public ResponseEntity<?> update(FeatureRequest feature) {
        FeatureResponse response = management.update(feature);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String code) {
        FeatureResponse response = management.delete(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping
    public ResponseEntity<?> showAllFeatures(Pageable pageable) {
        Page<FeatureResponse> responses = management.findAllFeatures(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
