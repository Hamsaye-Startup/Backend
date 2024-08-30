package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.storages.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.responses.StorageResponse;
import com.microservices.warehouse.storages.services.FeatureServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final MessageMapper mapper;
    private final FeatureServiceManagement featureServiceManagement;

    private UUID findUserByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    @PostMapping
    public ResponseEntity<?> addFeature(
            @RequestBody FeatureDTO feature
    ) {
        FeatureDTO response = featureServiceManagement.insertFeature(feature);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping
    public ResponseEntity<?> updateFeature(
            @RequestBody FeatureDTO feature
    ) {
        FeatureDTO response = featureServiceManagement.updateFeature(feature);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping("/storage/id/{storageId}")
    public ResponseEntity<?> updateStorageFeatures(
            @PathVariable("storageId") Long storageId,
            @RequestBody List<String> featureCodes,
            HttpServletRequest request
    ) {
        StorageResponse response = featureServiceManagement.updateStorageFeatureByCodes(
                storageId,
                featureCodes,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> deleteFeature(
            @PathVariable("code") String code
    ) {
        FeatureDTO response = featureServiceManagement.deleteFeatureByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findFeatureByCode(
            @PathVariable("code") String code
    ) {
        FeatureDTO response = featureServiceManagement.findFeatureByCode(code);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping
    public ResponseEntity<?> findAllFeatures(
            Pageable pageable
    ) {
        Page<FeatureDTO> responses = featureServiceManagement.findAllFeatures(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllFeaturesByStorageId(
            @PathVariable("storageId") Long storageId
    ) {
        List<FeatureDTO> responses = featureServiceManagement.findAllFeaturesByStorageId(
                storageId
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
