package com.microservices.user.roles.controllers;

import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import com.microservices.user.roles.services.RoleServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Handles HTTP requests related to role management.
 * Provides endpoints for creating, updating, deleting, and retrieving roles.
 * Also provides endpoints for retrieving role authorities.
 *
 * @see RoleRequest
 * @see RoleResponse
 * @see RoleServiceManagement
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceManagement management;

    /**
     * Creates a new role based on the provided {@link RoleRequest}.
     *
     * @param request The request containing details of the role to be created.
     * @return A {@link ResponseEntity} containing the created {@link RoleResponse}.
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> add(
            @Valid @RequestBody RoleRequest request
    ) {
        RoleResponse response = management.add(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing role identified by {@code roleId} with the details provided in {@link RoleRequest}.
     *
     * @param roleId The ID of the role to be updated.
     * @param request The request containing updated details of the role.
     * @return A {@link ResponseEntity} containing the updated {@link RoleResponse}.
     * @since 1.0
     */
    @PutMapping("/id/{roleId}")
    public ResponseEntity<?> update(
            @PathVariable("roleId") UUID roleId,
            @Valid @RequestBody RoleRequest request
    ) {
        RoleResponse response = management.update(roleId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes the role identified by {@code id}.
     *
     * @param uid The ID of the role to be deleted.
     * @return A {@link ResponseEntity} containing the deleted {@link RoleResponse}.
     * @since 1.0
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID uid
    ) {
        RoleResponse response = management.delete(uid);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all roles with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link ResponseEntity} containing a {@link Page} of {@link RoleResponse}.
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> showAllRoles(Pageable pageable) {
        Page<RoleResponse> responses = management.findAllRoles(pageable);
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves the role identified by {@code id}.
     *
     * @param uid The ID of the role to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link RoleResponse} for the requested role.
     * @since 1.0
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showRoleById(
            @PathVariable("id") UUID uid
    ) {
        RoleResponse response = management.findById(uid);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all authorities.
     *
     * @return A {@link ResponseEntity} containing a {@link Set} of authorities.
     * @since 1.0
     */
    @GetMapping("/authorities")
    public ResponseEntity<?> showAllAuthorities() {
        Set<String> responses = management.findAllAuthorities();
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all authorities for the role identified by {@code id}.
     *
     * @param uid The ID of the role whose authorities are to be retrieved.
     * @return A {@link ResponseEntity} containing a {@link Set} of authorities for the specified role.
     * @since 1.0
     */
    @GetMapping("/authorities/id/{id}")
    public ResponseEntity<?> showAllAuthorities(
            @PathVariable("id") UUID uid
    ) {
        Set<String> responses = management.findAllAuthorities(uid);
        return ResponseEntity.ok(responses);
    }
}
