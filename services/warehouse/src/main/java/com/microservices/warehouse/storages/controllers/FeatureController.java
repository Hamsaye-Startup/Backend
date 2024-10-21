package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.application.mapper.MessageMapper;
import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.application.exceptions.AuthenticationCredentialNotFoundException;
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

/**
 * This controller handles HTTP requests related to features of storage items.
 * It interacts with the {@link FeatureServiceManagement} to manage features and updates for storage.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage/feature")
@RequiredArgsConstructor
public class FeatureController {

    /**
     * Mapper for transforming between different representations of features.
     * See {@link com.microservices.warehouse.application.mapper.MessageMapper} for more details.
     */
    private final MessageMapper mapper;

    /**
     * Service management for handling feature-related operations.
     * See {@link com.microservices.warehouse.storages.services.FeatureServiceManagement} for more details.
     */
    private final FeatureServiceManagement featureServiceManagement;

    /**
     * Extracts the user ID from the HTTP request header, throwing an exception if not found.
     * @param request the HTTP request containing the user ID header
     * @return the extracted user ID as a {@link UUID}
     * @throws AuthenticationCredentialNotFoundException if the user ID header is missing
     * @since 1.0
     */
    private UUID findUserByHeaderOrThrow(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    /**
     * Extracts the user ID from the HTTP request header, returning null if not found.
     * @param request the HTTP request containing the user ID header
     * @return the extracted user ID as a {@link UUID}, or null if the header is missing
     * @since 1.0
     */
    private UUID findUserByHeaderOrNull(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            return null;
        }
        return UUID.fromString(userId);
    }

    /**
     * Adds a new feature to the system.
     * @param feature the feature to be added
     * @return ResponseEntity containing the added feature information
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> addFeature(
            @RequestBody FeatureDTO feature,
            HttpServletRequest request
    ) {
        FeatureDTO response = featureServiceManagement.insertFeature(feature);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Updates an existing feature in the system.
     * @param feature the feature with updated information
     * @return ResponseEntity containing the updated feature information
     * @since 1.0
     */
    @PutMapping
    public ResponseEntity<?> updateFeature(
            @RequestBody FeatureDTO feature,
            HttpServletRequest request
    ) {
        FeatureDTO response = featureServiceManagement.updateFeature(feature);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Updates features for a specific storage item based on feature codes.
     * @param storageId the ID of the storage item
     * @param featureCodes the list of feature codes to be updated
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the updated storage information
     * @since 1.0
     */
    @PutMapping("/storage/id/{storageId}")
    public ResponseEntity<?> updateStorageFeatures(
            @PathVariable("storageId") Long storageId,
            @RequestBody List<String> featureCodes,
            HttpServletRequest request
    ) {
        StorageResponse response = featureServiceManagement.updateStorageFeatureByCodes(
                storageId,
                featureCodes,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Deletes a feature by its code.
     * @param code the code of the feature to be deleted
     * @return ResponseEntity containing the deleted feature information
     * @since 1.0
     */
    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> deleteFeature(
            @PathVariable("code") String code,
            HttpServletRequest request
    ) {
        FeatureDTO response = featureServiceManagement.deleteFeatureByCode(code);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds a feature by its code.
     * @param code the code of the feature to be found
     * @return ResponseEntity containing the found feature information
     * @since 1.0
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<?> findFeatureByCode(
            @PathVariable("code") String code,
            HttpServletRequest request
    ) {
        FeatureDTO response = featureServiceManagement.findFeatureByCode(code);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds all features with pagination support.
     * @param pageable pagination information
     * @return ResponseEntity containing a page of features
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> findAllFeatures(
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<FeatureDTO> responses = featureServiceManagement.findAllFeatures(pageable);
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds all features associated with a specific storage item.
     * @param storageId the ID of the storage item
     * @return ResponseEntity containing a list of features associated with the storage item
     * @since 1.0
     */
    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllFeaturesByStorageId(
            @PathVariable("storageId") Long storageId,
            HttpServletRequest request
    ) {
        List<FeatureDTO> responses = featureServiceManagement.findAllFeaturesByStorageId(
                storageId
        );
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }
}
