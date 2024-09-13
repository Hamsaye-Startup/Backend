package com.microservices.user.users.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import com.microservices.user.users.services.UserServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Rest controller for handling user-related requests in the application.
 * It provides endpoints for registering, updating, deleting, and retrieving user data,
 * as well as blocking and unblocking users.
 *
 * <p>This controller uses {@link UserServiceManagement} for business logic and {@link MessageMapper} for mapping
 * responses. It also includes helper methods for checking user authorities.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceManagement management;
    private final MessageMapper mapper;

    /**
     * Helper method to check if a user has the required authority based on the request header.
     *
     * @param request The HTTP request containing the headers.
     * @param admin The role or authority to check.
     * @return True if the header contains the specified role, false otherwise.
     * @since 1.0
     */
    private boolean findAuthorityByHeader(HttpServletRequest request, String admin) {
        return Stream.of(request.getHeader("X_ROLE_A"))
                .anyMatch(s -> s.equals(admin));
    }

    /**
     * Registers a new user.
     *
     * @param request The {@link RegistrationRequest} containing the registration data.
     * @return A {@link ResponseEntity} containing the registered {@link UserResponse}.
     * @since 1.0
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        UserResponse response = management.register(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates a user by ID.
     *
     * @param userId The UUID of the user to update.
     * @param userRequest The {@link UserRequest} containing the updated data.
     * @param request The HTTP request used to check user authority.
     * @return A {@link ResponseEntity} containing the updated {@link UserResponse}.
     * @since 1.0
     */
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

    /**
     * Deletes a user by ID.
     *
     * @param uid The UUID of the user to delete.
     * @return A {@link ResponseEntity} containing the deleted {@link UserResponse}.
     * @since 1.0
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.delete(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable The pagination information.
     * @return A {@link ResponseEntity} containing a paginated list of {@link UserResponse}.
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> showAllUsers(
            Pageable pageable
    ) {
        Page<UserResponse> responses = management.findAllUsers(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The UUID of the user to retrieve.
     * @return A {@link ResponseEntity} containing the {@link UserResponse}.
     * @since 1.0
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showUserById(
            @PathVariable("id") UUID userId
    ) {
        UserResponse response = management.findById(userId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Blocks a user by their ID.
     *
     * @param uid The UUID of the user to block.
     * @return A {@link ResponseEntity} containing the blocked {@link UserResponse}.
     * @since 1.0
     */
    @PostMapping("/block/id/{id}")
    public ResponseEntity<?> blockUser(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.blockUser(uid, false);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Unblocks a user by their ID.
     *
     * @param uid The UUID of the user to unblock.
     * @return A {@link ResponseEntity} containing the unblocked {@link UserResponse}.
     * @since 1.0
     */
    @PostMapping("/unblock/id/{id}")
    public ResponseEntity<?> unblockUser(
            @PathVariable("id") UUID uid
    ) {
        UserResponse response = management.blockUser(uid, true);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
