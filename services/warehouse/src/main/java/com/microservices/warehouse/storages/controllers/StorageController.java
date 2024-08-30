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

@RestController
@RequestMapping("api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final MessageMapper mapper;
    private final StorageServiceManagement storageServiceManagement;

    private UUID findUserByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    @PostMapping
    public ResponseEntity<?> addStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            HttpServletRequest request
    ) {
        StorageResponse response = storageServiceManagement.insertStorage(
                storageRequest,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

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

    @PutMapping("/id/{storageId}/verify")
    public ResponseEntity<?> verifyStorage(@PathVariable("storageId") Long id) {
        StorageResponse response = storageServiceManagement.verifyStorageById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/id/{storageId}/host")
    public ResponseEntity<?> removeStorageByHost(@PathVariable("storageId") Long id) {
        StorageResponse response = storageServiceManagement.removeStorageById(id, StorageStatusEnum.ON_BLOCK_STASH);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PostMapping("/id/{storageId}/display")
    public ResponseEntity<?> showStorage(
            @PathVariable("storageId") Long id,
            @RequestParam(name = "displayable") boolean enabled
    ) {
        StorageResponse response = storageServiceManagement.displayStorage(id, enabled);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/id/{storageId}/details")
    public ResponseEntity<?> findStorageInDetail(
            @PathVariable("storageId") Long id,
            HttpServletRequest request
    ) {
        StorageResponse response = storageServiceManagement.findStorageById(
                id,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/host")
    public ResponseEntity<?> findStoragesByHost(
            @RequestParam(name = "uid") UUID userId,
            Pageable pageable
    ) {
        Page<StorageResponse> responses = storageServiceManagement.findStoragesByUserId(userId, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping
    public ResponseEntity<?> findStorages(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "from") LocalDate fromDate,
            @RequestParam(name = "to") LocalDate toDate,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<StorageResponse> responses = storageServiceManagement.findStorages(
                category,
                fromDate,
                toDate,
                pageable,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStorages(
            @RequestParam(name = "value") String value,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<StorageResponse> responses = storageServiceManagement.searchStorages(
                value,
                pageable,
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
