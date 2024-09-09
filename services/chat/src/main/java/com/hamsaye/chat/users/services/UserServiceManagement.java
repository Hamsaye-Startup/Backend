package com.hamsaye.chat.users.services;

import com.hamsaye.chat.kafka.producers.UserProducerService;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.ConnectionStatus;
import com.hamsaye.chat.users.models.UserConnectionState;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserNotifyRequest;
import com.hamsaye.chat.users.requests.UserNotifyType;
import com.hamsaye.chat.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for managing user-related operations including disconnecting users and retrieving user information.
 * <p>
 * This service interacts with the {@link UserService} for user data access, the {@link UserMapper} for
 * mapping entities to responses, and the {@link UserProducerService} for sending notifications about user events.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceManagement {

    /**
     * Service for user data access.
     *
     * @see com.hamsaye.chat.users.services.UserService
     */
    private final UserService userService;

    /**
     * Mapper for converting between user entities and user data transfer objects.
     *
     * @see com.hamsaye.chat.users.mappers.UserMapper
     */
    private final UserMapper userMapper;

    /**
     * Service for producing Kafka messages related to user events.
     *
     * @see com.hamsaye.chat.kafka.producers.UserProducerService
     */
    private final UserProducerService userProducerService;

    /**
     * Disconnects a user and sends a notification about the disconnection event.
     * <p>
     * This method updates the user's connection state to disconnected and sends a notification message
     * via the Kafka producer service.
     * </p>
     *
     * @param uid the UUID of the user to disconnect
     * @param socketId the WebSocket session ID of the user
     * @return a {@link UserResponse} representing the updated state of the user
     * @since 1.0
     */
    public UserResponse disconnectUser(UUID uid, String socketId) {

        // update the user connection to disconnect
        UserEntity user = userService.findUserById(uid);
        user.setConnectionState(
                UserConnectionState.builder()
                        .socketId(socketId)
                        .initiatedAt(LocalDateTime.now())
                        .connectionStatus(ConnectionStatus.DISCONNECTED)
                        .build()
        );

        // produce disconnect message
        userProducerService.send(UserNotifyRequest.builder()
                .userInfo(userMapper.toDTO(user))
                .type(UserNotifyType.DISCONNECTED_USER)
                .message(UserNotifyType.DISCONNECTED_USER.getMessage())
                .build());

        return userMapper.toResponse(user);
    }

    /**
     * Retrieves user information based on the provided user ID.
     *
     * @param uid the UUID of the user to retrieve
     * @return a {@link UserResponse} containing the user's details
     * @since 1.0
     */
    public UserResponse findUserById(UUID uid) {
        return userMapper.toResponse(userService.findUserById(uid));
    }
}
