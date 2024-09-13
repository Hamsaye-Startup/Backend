package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.kafka.requests.UserDetailNotifyRequest;
import com.hamsaye.chat.kafka.requests.UserDetailNotifyType;
import com.hamsaye.chat.kafka.requests.UserNotifyRequest;
import com.hamsaye.chat.kafka.requests.UserNotifyType;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.UserAttribute;
import com.hamsaye.chat.users.models.UserContactInfo;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.models.GenderEnum;
import com.hamsaye.chat.users.models.UserLoyaltyStatus;
import com.hamsaye.chat.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for consuming Kafka messages related to user notifications.
 * <p>
 * This service listens to Kafka topics for user notifications and updates the user information
 * in the system based on the type of notification received. It supports creating new users,
 * updating existing user information, and deleting users.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailConsumerService {

    /**
     * Service for user-related operations.
     * @see com.hamsaye.chat.users.services.UserService
     */
    private final UserService userService;

    /**
     * Mapper for user-related transformations.
     * @see com.hamsaye.chat.users.mappers.UserMapper
     */
    private final UserMapper userMapper;

    /**
     * Template for sending WebSocket messages.
     * @see org.springframework.messaging.simp.SimpMessagingTemplate
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Mapper for response message transformations.
     * @see com.hamsaye.chat.applications.mapper.ResponseMessageMapper
     */
    private final ResponseMessageMapper mapper;

    /**
     * Listens to Kafka messages related to general user notifications.
     * <p>
     * This method processes notifications for new users, updates to existing users, and
     * deletions of users. It updates the user information in the system and sends
     * updates via WebSocket.
     * </p>
     *
     * @param request the {@link UserNotifyRequest} containing user notification details
     * @param key the Kafka message key (optional)
     * @since 1.0
     */
    @KafkaListener(
            id = "external-user-info-listener-id",
            topics = "topic-general-customers",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void generalUserListener(
            UserNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "external-user-info-listener-id",
                request
        );

        if (request.type().equals(UserNotifyType.NEW_USER)) {

            // generate the user
            UserEntity user = generateUserByUserNotification(request);
            UserEntity saved = userService.saveUser(user);

            // update the websocket
            messagingTemplate.convertAndSend(
                    "/topic/users",
                    ResponseEntity.ok(mapper.toResponse(userMapper.toResponse(saved)))
            );

        } else if (request.type().equals(UserNotifyType.UPDATE_USER)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.userInfo().uid());

            // update the user information
            UserEntity updated = updateUserByUserNotification(user, request);
            userService.updateUser(updated);

        } else if (request.type().equals(UserNotifyType.DELETE_USER)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.userInfo().uid());
            userService.deleteUser(user);

        }
    }

    /**
     * Listens to Kafka messages related to customer detail notifications.
     * <p>
     * This method processes notifications for new customer details and updates to existing customer
     * details. It updates the user information in the system based on the notification received.
     * </p>
     *
     * @param request the {@link UserDetailNotifyRequest} containing customer notification details
     * @param key the Kafka message key (optional)
     * @since 1.0
     */
    @KafkaListener(
            id = "external-user-detail-info-listener-id",
            topics = "topic-general-user-details",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void userDetailListener(
            UserDetailNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "external-user-detail-info-listener-id",
                request
        );

        if (request.type().equals(UserDetailNotifyType.NEW_USER) || request.type().equals(UserDetailNotifyType.UPDATE_USER_INFO)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.userInfo().uid());

            // update the user information
            UserEntity updated = updateUserByCustomerNotification(user, request);
            userService.updateUser(updated);

        }
    }

    /**
     * Generates a {@link UserEntity} based on the user notification request.
     * <p>
     * This method creates a new {@link UserEntity} with the details provided in the user notification request.
     * </p>
     *
     * @param request the {@link UserNotifyRequest} containing user information
     * @return the generated {@link UserEntity}
     * @since 1.0
     */
    private UserEntity generateUserByUserNotification(UserNotifyRequest request) {
        return UserEntity.builder()
                .uid(request.userInfo().uid())
                .firstname(request.userInfo().firstname())
                .lastname(request.userInfo().lastname())
                .contact(UserContactInfo.builder()
                        .phone(request.userInfo().phone())
                        .build())
                .profilePictureId(request.userInfo().profilePictureId())
                .build();
    }

    /**
     * Updates a {@link UserEntity} based on the user notification request.
     * <p>
     * This method updates the existing user entity with the details provided in the user notification request.
     * </p>
     *
     * @param user the {@link UserEntity} to be updated
     * @param request the {@link UserNotifyRequest} containing updated user information
     * @return the updated {@link UserEntity}
     * @since 1.0
     */
    private UserEntity updateUserByUserNotification(UserEntity user, UserNotifyRequest request) {
        user.setFirstname(request.userInfo().firstname());
        user.setLastname(request.userInfo().lastname());
        user.setContact(UserContactInfo.builder()
                .phone(request.userInfo().phone())
                .build());
        user.setProfilePictureId(request.userInfo().profilePictureId());
        return user;
    }

    /**
     * Updates a {@link UserEntity} based on the user notification request.
     * <p>
     * This method updates the existing user entity with customer-specific details provided in the customer
     * notification request.
     * </p>
     *
     * @param user the {@link UserEntity} to be updated
     * @param request the {@link UserDetailNotifyRequest} containing updated user information
     * @return the updated {@link UserEntity}
     * @since 1.0
     */
    private UserEntity updateUserByCustomerNotification(UserEntity user, UserDetailNotifyRequest request) {
        user.setAttribute(UserAttribute.builder()
                .gender(GenderEnum.valueOf(request.userInfo().gender().name()))
                .build());
        user.setLoyaltyStatus(UserLoyaltyStatus.valueOf(request.userInfo().loyalty().name()));
        return user;
    }
}
