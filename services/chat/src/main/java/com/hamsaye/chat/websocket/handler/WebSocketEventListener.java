package com.hamsaye.chat.websocket.handler;

import com.hamsaye.chat.users.models.ConnectionStatus;
import com.hamsaye.chat.users.models.UserConnectionState;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final UserService userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        // find the user from header
        Object userId = headerAccessor.getHeader("X_USER_ID");

        if (userId != null) {

            // find the user
            UserEntity user = userService.findUserById(UUID.fromString((String) userId));
            userService.connectUser(user, UserConnectionState.builder()
                    .socketId(sessionId)
                    .initiatedAt(LocalDateTime.now())
                    .connectionStatus(ConnectionStatus.CONNECTED)
                    .build());

            System.out.println("session is connected!!!");
        }

        log.info("connect listener is working");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        // find the user from header
        Object userId = headerAccessor.getHeader("X_USER_ID");

        if (userId != null) {

            // find the user
            UserEntity user = userService.findUserById(UUID.fromString((String) userId));
            userService.connectUser(user, UserConnectionState.builder()
                    .socketId(sessionId)
                    .initiatedAt(LocalDateTime.now())
                    .connectionStatus(ConnectionStatus.DISCONNECTED)
                    .build());

            System.out.println("session is disconnected!!!");
        }

        log.info("disconnect listener is working");
    }
}
