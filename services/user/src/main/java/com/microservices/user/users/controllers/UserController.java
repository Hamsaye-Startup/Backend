package com.microservices.user.users.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import com.microservices.user.users.services.UserServiceManagement;
import com.microservices.user.utils.log.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceManagement management;
    private final MessageMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        UserResponse response = management.register(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request, Principal principal) {
        UserResponse response = management.update(userRequest, request.getHeader("scope"), principal);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID uid) {
        UserResponse response = management.delete(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @GetMapping
    public ResponseEntity<?> showAllUsers(@RequestParam(name = "offset", required = false) LocalDateTime offset) {
        List<UserResponse> responses = management.findAllUsers(offset);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showUserById(@PathVariable("id") UUID uid, HttpServletRequest request, Principal principal) {
        UserResponse response = management.findById(uid, request.getHeader("scope"), principal);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PreAuthorize("hasAuthority('BLOCK_USER')")
    @PostMapping("/block/id/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") UUID uid) {
        UserResponse response = management.blockUser(uid, false);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PreAuthorize("hasAuthority('BLOCK_USER')")
    @PostMapping("/unblock/id/{id}")
    public ResponseEntity<?> unblockUser(@PathVariable("id") UUID uid) {
        UserResponse response = management.blockUser(uid, true);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
