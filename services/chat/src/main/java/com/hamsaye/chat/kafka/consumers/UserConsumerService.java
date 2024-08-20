package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.ConnectionStatus;
import com.hamsaye.chat.users.models.UserConnectionState;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserNotifyRequest;
import com.hamsaye.chat.users.requests.UserNotifyType;
import com.hamsaye.chat.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumerService {

    private final UserService userService;
    private final UserMapper userMapper;

    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseMessageMapper mapper;

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
                "external-user-info-listener-id",
                request
        );

        // find the user by id
        UserEntity user = userService.findUserById(request.userInfo().uid());

        // update the user connection state
        UserEntity disconnectedUser = userService.connectUser(
                user,
                user.getConnectionState()
        );

        // update the user by websocket
        messagingTemplate.convertAndSend(
                "/user/public",
                ResponseEntity.ok(mapper.toResponse(userMapper.toResponse(disconnectedUser)))
        );
    }
}
