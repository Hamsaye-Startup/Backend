package com.microservices.user.users.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.users.services.UserDetailServiceManagement;
import com.microservices.user.users.requests.UserDetailRequest;
import com.microservices.user.users.responses.UserDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing customer-related operations.
 * This controller handles requests related to customer registration, updates, deletions,
 * retrieval by ID, and listing all customers. It interacts with the {@link UserDetailServiceManagement} service
 * to perform these operations and maps the results to appropriate response DTOs.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailServiceManagement service;

    /**
     * @see com.microservices.user.application.mapper.MessageMapper
     */
    private final MessageMapper mapper;

    /**
     * Registers a new user detail.
     *
     * @param request the {@link UserDetailRequest} containing user registration details.
     * @return a {@link ResponseEntity} containing the registered {@link UserDetailResponse}.
     * @since 1.0
     */
    @PostMapping("/id/{userId}/detail")
    public ResponseEntity<?> register(
            @PathVariable("userId") UUID userId,
            @Valid @RequestBody UserDetailRequest request
    ) {
        UserDetailResponse response = service.register(request, userId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates an existing user's details.
     *
     * @param request the {@link UserDetailRequest} containing updated user information.
     * @return a {@link ResponseEntity} containing the updated {@link UserDetailResponse}.
     * @since 1.0
     */
    @PutMapping("/id/{userId}/detail")
    public ResponseEntity<?> update(
            @PathVariable("userId") UUID userId,
            @Valid @RequestBody UserDetailRequest request
    ) {
        UserDetailResponse response = service.update(request, userId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Deletes a user by their unique ID.
     *
     * @param uid the UUID of the user to be deleted.
     * @return a {@link ResponseEntity} containing the deleted {@link UserDetailResponse}.
     * @since 1.0
     */
    @DeleteMapping("/id/{userId}/detail")
    public ResponseEntity<?> delete(@PathVariable("userId") UUID uid) {

        UserDetailResponse response = service.deleteByUserId(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds a user detail by their unique ID.
     *
     * @param uid the UUID of the user to be retrieved.
     * @return a {@link ResponseEntity} containing the {@link UserDetailResponse} for the specified user detail.
     * @since 1.0
     */
    @GetMapping("/id/{userId}/detail")
    public ResponseEntity<?> findById(
            @PathVariable("userId") UUID uid
    ) {
        UserDetailResponse response = service.findByUserId(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/online/role/id/{roleId}/detail")
    public ResponseEntity<?> findOnlineUsersByRole(
            @PathVariable("roleId") UUID rid,
            Pageable pageable
    ) {
        Page<UserDetailResponse> responses = service.findAllOnlineUsersByRoleId(
                rid,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
