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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceManagement management;

    //@PreAuthorize("hasAuthority('WRITE_ROLE')")
    @PostMapping
    public ResponseEntity<?> add(
            @Valid @RequestBody RoleRequest request
    ) {
        RoleResponse response = management.add(request);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('WRITE_ROLE')")
    @PutMapping("/id/{roleId}")
    public ResponseEntity<?> update(
            @PathVariable("roleId") UUID roleId,
            @Valid @RequestBody RoleRequest request
    ) {
        RoleResponse response = management.update(roleId, request);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID uid
    ) {
        RoleResponse response = management.delete(uid);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('READ_ROLES')")
    @GetMapping
    public ResponseEntity<?> showAllRoles(Pageable pageable) {
        Page<RoleResponse> responses = management.findAllRoles(pageable);
        return ResponseEntity.ok(responses);
    }

    //@PreAuthorize("hasAuthority('READ_ROLE')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showRoleById(
            @PathVariable("id") UUID uid
    ) {
        RoleResponse response = management.findById(uid);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('READ_ROLES')")
    @GetMapping("/authorities")
    public ResponseEntity<?> showAllAuthorities() {
        Set<String> responses = management.findAllAuthorities();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/authorities/id/{id}")
    public ResponseEntity<?> showAllAuthorities(
            @PathVariable("id") UUID uid
    ) {
        Set<String> responses = management.findAllAuthorities(uid);
        return ResponseEntity.ok(responses);
    }
}
