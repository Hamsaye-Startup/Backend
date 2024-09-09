package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.geos.requests.AddressRequests;
import com.microservices.warehouse.storages.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.models.StorageStatusEnum;
import com.microservices.warehouse.storages.requests.StorageRequest;
import com.microservices.warehouse.storages.responses.StorageResponse;
import com.microservices.warehouse.storages.services.StorageServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * This controller handles HTTP requests related to storage operations.
 * It interacts with the {@link StorageServiceManagement} to manage storage items.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    /**
     * Mapper for transforming between different representations of storage information.
     * See {@link com.microservices.warehouse.applications.mapper.MessageMapper} for more details.
     */
    private final MessageMapper mapper;

    /**
     * Service management for handling storage-related operations.
     * See {@link com.microservices.warehouse.storages.services.StorageServiceManagement} for more details.
     */
    private final StorageServiceManagement storageServiceManagement;

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
     * Adds a new storage item.
     * @param storageRequest the storage item to be added
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the added storage information
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> addStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            HttpServletRequest request
    ) {
        StorageResponse response = storageServiceManagement.insertStorage(
                storageRequest,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates the address of a storage item.
     * @param id the ID of the storage item to be updated
     * @param addressRequests the new address information
     * @return ResponseEntity containing the updated storage information
     * @since 1.0
     */
    @PutMapping("/id/{storageId}/address")
    public ResponseEntity<?> updateStorageAddress(
            @PathVariable("storageId") Long id,
            @RequestBody @Valid AddressRequests addressRequests
    ) {
        StorageResponse response = storageServiceManagement.updateStorageAddress(
                id,
                addressRequests
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates the details of a storage item.
     * @param id the ID of the storage item to be updated
     * @param storageRequest the new storage details
     * @return ResponseEntity containing the updated storage information
     * @since 1.0
     */
    @PutMapping("/id/{storageId}/details")
    public ResponseEntity<?> updateStorageDetails(
            @PathVariable("storageId") Long id,
            @RequestBody @Valid StorageRequest storageRequest
    ) {
        StorageResponse response = storageServiceManagement.updateStorageDetails(
                id,
                storageRequest
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Verifies a storage item by its ID.
     * @param id the ID of the storage item to be verified
     * @return ResponseEntity containing the verified storage information
     * @since 1.0
     */
    @PutMapping("/id/{storageId}/verify")
    public ResponseEntity<?> verifyStorage(@PathVariable("storageId") Long id) {
        StorageResponse response = storageServiceManagement.verifyStorageById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Removes a storage item by its ID, setting its status to ON_BLOCK_STASH.
     * @param id the ID of the storage item to be removed
     * @return ResponseEntity containing the removed storage information
     * @since 1.0
     */
    @DeleteMapping("/id/{storageId}/host")
    public ResponseEntity<?> removeStorageByHost(@PathVariable("storageId") Long id) {
        StorageResponse response = storageServiceManagement.removeStorageById(id, StorageStatusEnum.ON_BLOCK_STASH);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates the display status of a storage item.
     * @param id the ID of the storage item to be updated
     * @param enabled true to make the storage displayable, false to hide it
     * @return ResponseEntity containing the updated storage information
     * @since 1.0
     */
    @PostMapping("/id/{storageId}/display")
    public ResponseEntity<?> showStorage(
            @PathVariable("storageId") Long id,
            @RequestParam(name = "displayable") boolean enabled
    ) {
        StorageResponse response = storageServiceManagement.displayStorage(id, enabled);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds a storage item in detail by its ID.
     * @param id the ID of the storage item to be found
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the detailed storage information
     * @since 1.0
     */
    @GetMapping("/id/{storageId}/details")
    public ResponseEntity<?> findStorageInDetail(
            @PathVariable("storageId") Long id,
            HttpServletRequest request
    ) {

        UUID userId = findUserByHeaderOrNull(request);
        StorageResponse response;
        if (userId != null) {
            response = storageServiceManagement.findStorageById(
                    id,
                    userId
            );
        }
        else {
            response = storageServiceManagement.findStorageById(id);
        }

        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds all storage items associated with a specific host user.
     * @param userId the ID of the host user
     * @param pageable pagination information
     * @return ResponseEntity containing a page of storage items
     * @since 1.0
     */
    @GetMapping("/host")
    public ResponseEntity<?> findStoragesByHost(
            @RequestParam(name = "uid") UUID userId,
            Pageable pageable
    ) {
        Page<StorageResponse> responses = storageServiceManagement.findStoragesByUserId(userId, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Finds all storage items based on category, date range, and pagination.
     * @param category the category of storage items
     * @param fromDate the start date of the date range
     * @param toDate the end date of the date range
     * @param pageable pagination information
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing a page of storage items
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> findStorages(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "from") LocalDate fromDate,
            @RequestParam(name = "to") LocalDate toDate,
            Pageable pageable,
            HttpServletRequest request
    ) {
        UUID userId = findUserByHeaderOrNull(request);
        Page<StorageResponse> responses;
        if (userId != null) {
            responses = storageServiceManagement.findStorages(
                    category,
                    fromDate,
                    toDate,
                    pageable,
                    userId
            );
        }
        else {
            responses = storageServiceManagement.findStorages(
                    category,
                    fromDate,
                    toDate,
                    pageable
            );
        }

        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Searches for storage items based on a search value, with pagination.
     * @param value the search value
     * @param pageable pagination information
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing a page of search results
     * @since 1.0
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchStorages(
            @RequestParam(name = "value") String value,
            Pageable pageable,
            HttpServletRequest request
    ) {
        UUID userId = findUserByHeaderOrNull(request);
        Page<StorageResponse> responses;
        if (userId != null) {
            responses = storageServiceManagement.searchStorages(
                    value,
                    pageable,
                    userId
            );
        }
        else {
            responses = storageServiceManagement.searchStorages(
                    value,
                    pageable
            );
        }

        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
