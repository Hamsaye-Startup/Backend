package com.microservices.user.users.services;

import com.microservices.user.kafka.producers.UserProducerService;
import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.services.RoleService;
import com.microservices.user.users.mappers.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.kafka.requests.UserNotifyRequest;
import com.microservices.user.kafka.requests.UserNotifyType;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service class that manages user-related operations and interactions with the {@link UserService},
 * {@link RoleService}, and {@link UserProducerService}.
 * This service provides functionality to register, update, delete, and find users, as well as
 * manage user notifications.
 *
 * @see UserService
 * @see RoleService
 * @see UserProducerService
 * @see UserMapper
 * @see UserEntity
 * @see PasswordEntity
 * @see RoleEntity
 * @see RegistrationRequest
 * @see UserRequest
 * @see UserResponse
 * @see UserNotifyRequest
 * @see UserNotifyType
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceManagement {

    private final UserMapper mapper;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserProducerService userProducerService;

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param userRequest The registration request containing user details.
     * @return The {@link UserResponse} of the registered user.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse register(RegistrationRequest userRequest) {

        // generate a user entity
        UserEntity user = mapper.toUserEntity(userRequest);

        // fetch the customer role
        RoleEntity customerRole = roleService.findRoleByName("role_default");

        // generate the password entity
        PasswordEntity password = PasswordEntity.builder()
                .password(passwordEncoder.encode(userRequest.password()))
                .expiredAt(LocalDateTime.now().plusDays(100))
                .build();

        UserEntity persisted = userService.persist(user, customerRole, password);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(persisted))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(persisted);
    }

    /**
     * Updates an existing user based on the provided user ID and request.
     *
     * @param userId The UUID of the user to be updated.
     * @param request The request containing updated user details.
     * @param admin Flag indicating if the update is performed by an admin.
     * @return The {@link UserResponse} of the updated user.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse update(UUID userId, UserRequest request, boolean admin) {

        // find the user by uid
        UserEntity exist = userService.findByUid(userId);

        // update the user information
        UserEntity user = mapper.toUserEntity(request, exist);

        UserEntity updated;
        if (admin) {

            // find the role by id
            RoleEntity role = roleService.findRoleById(request.rid());
            updated = userService.update(user, role);
        }
        else {
            updated = userService.update(user);
        }

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(updated))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(updated);
    }

    /**
     * Deletes an existing user based on the provided user ID.
     *
     * @param uid The UUID of the user to be deleted.
     * @return The {@link UserResponse} of the deleted user.
     * @since 1.0
     */
    public UserResponse delete(UUID uid) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // delete the user
        UserEntity deleted = userService.delete(user);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(deleted))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(deleted);
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable Pagination details.
     * @return A {@link Page} of {@link UserResponse} instances.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<UserResponse> findAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param uid The UUID of the user to find.
     * @return The {@link UserResponse} of the found user.
     * @since 1.0
     */
    public UserResponse findById(UUID uid) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);
        return mapper.toResponse(user);
    }

    /**
     * Blocks or unblocks a user based on the provided user ID and unblock flag.
     *
     * @param uid The UUID of the user to be blocked or unblocked.
     * @param unblock Flag indicating if the user should be unblocked.
     * @return The {@link UserResponse} of the updated user.
     * @since 1.0
     */
    public UserResponse blockUser(UUID uid, boolean unblock) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // update the user
        user.setEnabled(unblock);
        UserEntity updated = userService.update(user);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(updated))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(updated);
    }
}
