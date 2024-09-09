package com.microservices.user.passwords.services;

import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.passwords.requests.PasswordRequest;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing password-related operations.
 * This includes updating passwords for users. It interacts with the {@link UserService} to perform user and password updates.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PasswordServiceManagement {

    private final UserService userService;
    private final PasswordEncoder encoder;

    /**
     * Updates the password for a user based on the provided request and the currently authenticated user.
     *
     * @param request The request containing the new password and user ID.
     * @throws IllegalRequestException if the user ID in the request does not match the authenticated user.
     * @since 1.0
     */
    public void update(PasswordRequest request) {

        // Fetch the user entity
        UserEntity user = userService.findByUid(request.uid());

        // Fetch the user's password entity and update it
        PasswordEntity password = user.getPasswordEntity();
        password.setPassword(encoder.encode(request.password()));

        // Persist the updated user and password information
        userService.persist(user, user.getRole(), password);
    }
}
