package com.microservices.user.passwords.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.passwords.requests.PasswordRequest;
import com.microservices.user.passwords.services.PasswordServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing passwords. This class provides APIs for updating and resetting passwords.
 * It interacts with the {@link PasswordServiceManagement} service class to perform the necessary operations.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordServiceManagement management;
    private final MessageMapper mapper;

    /**
     * Updates the password for the currently authenticated user.
     *
     * @param request The request payload containing the new password details.
     * @return A response entity with an HTTP status of OK if the update is successful.
     * @since 1.0
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody PasswordRequest request) {
        management.update(request);
        return new ResponseEntity<>(mapper.toResponse(), HttpStatus.OK);
    }

    /**
     * Resets the password for a user identified by the provided user ID.
     * This endpoint is currently a placeholder and needs implementation.
     *
     * @param uid The unique identifier of the user whose password is to be reset.
     * @return A response entity with a status indicating the result of the operation.
     * @since 1.0
     */
    @PostMapping("/reset/id/{id}")
    public ResponseEntity<?> reset(@PathVariable("id") UUID uid) {
        // TODO: change the password
        // TODO: send code by the SMS
        return null;
    }
}
