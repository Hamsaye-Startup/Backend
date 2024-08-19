package com.hamsaye.chat.users.services;

import com.hamsaye.chat.kafka.producers.UserProducerService;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.ConnectionStatus;
import com.hamsaye.chat.users.models.UserConnectionState;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserNotifyRequest;
import com.hamsaye.chat.users.requests.UserNotifyType;
import com.hamsaye.chat.users.requests.UserRequest;
import com.hamsaye.chat.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceManagement {

    private final UserService userService;
    private final UserMapper userMapper;

    private final UserProducerService userProducerService;


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

    public UserResponse findUserById(UUID uid) {
        return userMapper.toResponse(userService.findUserById(uid));
    }
}
