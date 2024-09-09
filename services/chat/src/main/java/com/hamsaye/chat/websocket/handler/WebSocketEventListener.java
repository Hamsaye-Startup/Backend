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

/**
 * Service for handling WebSocket connection and disconnection events.
 * <p>
 * This class listens for WebSocket connection and disconnection events to update the
 * user's connection state and status in the system. It integrates with the {@link UserService}
 * to find the user based on the provided user ID and updates their connection state accordingly.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final UserService userService;

    /**
     * Handles WebSocket connection events.
     * <p>
     * This method is triggered when a WebSocket connection is established. It extracts the
     * user ID from the WebSocket headers, finds the corresponding user, and updates their
     * connection state to connected. It logs the event and prints a message indicating
     * that the session is connected.
     * </p>
     *
     * @param event the {@link SessionConnectedEvent} representing the connection event
     * @since 1.0
     */
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

    /**
     * Handles WebSocket disconnection events.
     * <p>
     * This method is triggered when a WebSocket connection is closed. It extracts the
     * user ID from the WebSocket headers, finds the corresponding user, and updates their
     * connection state to disconnected. It logs the event and prints a message indicating
     * that the session is disconnected.
     * </p>
     *
     * @param event the {@link SessionDisconnectEvent} representing the disconnection event
     * @since 1.0
     */
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
