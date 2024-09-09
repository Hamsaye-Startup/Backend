package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserNotifyRequest;
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
 * Service class responsible for consuming Kafka messages related to user updates.
 * <p>
 * This service listens to Kafka topics for user notifications and updates user connection states
 * based on the notifications received. It sends updated user information via WebSocket to subscribed clients.
 * </p>
 *
 * @since 1.0
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumerService {

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
     * Listens to Kafka messages related to user updates.
     * <p>
     * This method processes notifications for user connection state updates and sends the updated user
     * information via WebSocket to subscribed clients.
     * </p>
     *
     * @param request the {@link UserNotifyRequest} containing user notification details
     * @param key the Kafka message key (optional)
     */
    @KafkaListener(
            id = "chat-user-listener-id",
            topics = "topic-chat-users",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void userListener(
            UserNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "chat-user-listener-id",
                request
        );

        // find the user by id
        UserEntity user = userService.findUserById(request.userInfo().uid());

        // update the user connection state
        UserEntity disconnectedUser = userService.connectUser(
                user,
                user.getConnectionState()
        );

        // return by websocket
        messagingTemplate.convertAndSend(
                "/topic/users",
                ResponseEntity.ok(mapper.toResponse(userMapper.toResponse(disconnectedUser)))
        );
    }
}
