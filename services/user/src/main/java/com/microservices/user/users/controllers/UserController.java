package com.microservices.user.users.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.users.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import com.microservices.user.users.services.UserServiceManagement;
import com.microservices.user.application.utils.log.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceManagement management;
    private final MessageMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    private boolean findAuthorityByHeader(HttpServletRequest request, String admin) {
        return Stream.of(request.getHeader("X_ROLE_A"))
                .anyMatch(s -> s.equals(admin));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        UserResponse response = management.register(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    //@PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID userId,
            @Valid @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ) {
        UserResponse response = management.update(
                userId,
                userRequest,
                findAuthorityByHeader(request, "UPDATE_USER_BY_ADMIN")
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    //@PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.delete(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    //@PreAuthorize("hasAuthority('READ_USERS')")
    @GetMapping
    public ResponseEntity<?> showAllUsers(
            Pageable pageable
    ) {
        Page<UserResponse> responses = management.findAllUsers(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    //@PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showUserById(
            @PathVariable("id") UUID userId
    ) {
        UserResponse response = management.findById(userId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    //@PreAuthorize("hasAuthority('BLOCK_USER')")
    @PostMapping("/block/id/{id}")
    public ResponseEntity<?> blockUser(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.blockUser(uid, false);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    //@PreAuthorize("hasAuthority('BLOCK_USER')")
    @PostMapping("/unblock/id/{id}")
    public ResponseEntity<?> unblockUser(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.blockUser(uid, true);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
