package com.microservices.user.roles.controllers;

import com.microservices.user.roles.models.UserAuthorityEnum;
import com.microservices.user.roles.requests.NewRollRequest;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import com.microservices.user.roles.services.RoleServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> add(@Valid @RequestBody NewRollRequest request) {
        RoleResponse response = management.add(request);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('WRITE_ROLE')")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody RoleRequest request) {
        RoleResponse response = management.update(request);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID uid) {
        RoleResponse response = management.delete(uid);
        return ResponseEntity.ok(response);
    }

    //@PreAuthorize("hasAuthority('READ_ROLES')")
    @GetMapping
    public ResponseEntity<?> showAllRoles(@RequestParam(name = "offset") LocalDateTime offset) {
        List<RoleResponse> responses = management.findAllRoles(offset);
        return ResponseEntity.ok(responses);
    }

    //@PreAuthorize("hasAuthority('READ_ROLE')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showRoleById(@PathVariable("id") UUID uid) {
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
    public ResponseEntity<?> showAllAuthorities(@PathVariable("id") UUID uid) {
        Set<String> responses = management.findAllAuthorities(uid);
        return ResponseEntity.ok(responses);
    }
}
