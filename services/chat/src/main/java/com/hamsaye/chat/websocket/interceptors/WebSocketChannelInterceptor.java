package com.hamsaye.chat.websocket.interceptors;

import com.hamsaye.chat.websocket.exceptions.AuthenticationCredentialNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Intercepts WebSocket messages to handle authentication and add additional headers.
 * <p>
 * This class implements {@link ChannelInterceptor} to process messages before they are sent.
 * It handles WebSocket connection events by checking for the presence of necessary headers
 * and performs authentication and logging. It sets a custom header for connection time.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    // private static final String SESSION_ID_HEADER = "SESSION_ID";
    // private static final String API_KEY_HEADER = "API_KEY";
    private static final String USER_ID_HEADER = "X_USER_ID";

    /**
     * Processes the message before sending it.
     * <p>
     * This method is invoked before a message is sent. It checks if the WebSocket command
     * is CONNECT and retrieves the user ID from the headers. It also sets a custom header
     * for the connection time and logs the connection event.
     * </p>
     *
     * @param message the message to be processed
     * @param channel the message channel
     * @return the processed message
     * @since 1.0
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = getAccessor(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {

            // String sessionId = readSessionIdHeader(accessor);
            // String apiKey = readAuthKeyHeader(accessor);
            String userId = readWebSocketUserIdHeader(accessor);

            // authenticate the user with username password authentication class
            // TODO: generate the username password authentication

            accessor.setHeader("connection-time", LocalDateTime.now());
            log.info("user with userId '{}' make websocket connection", userId);
        }
        return message;
    }

    /*private String readSessionIdHeader(StompHeaderAccessor accessor) {
        String session = accessor.getFirstNativeHeader(SESSION_ID_HEADER);
        if (session == null || session.trim().isEmpty()) {
            throw new AuthenticationCredentialNotFoundException("web socket session id header not found");
        }
        return session;
    }*/

    /*private String readAuthKeyHeader(StompHeaderAccessor accessor) {
        String key = accessor.getFirstNativeHeader(API_KEY_HEADER);
        if (key == null || key.trim().isEmpty()) {
            throw new AuthenticationCredentialNotFoundException("api auth key header not found");
        }
        return key;
    }*/

    private String readWebSocketUserIdHeader(StompHeaderAccessor accessor) {
        String id = accessor.getFirstNativeHeader(USER_ID_HEADER);
        if (id == null || id.trim().isEmpty()) {
            throw new AuthenticationCredentialNotFoundException("web socket user id header not found");
        }
        return id;
    }

    /**
     * Retrieves the {@link StompHeaderAccessor} from the message.
     * <p>
     * This method extracts the {@link StompHeaderAccessor} from the given message, which
     * is used to access and manipulate WebSocket headers.
     * </p>
     *
     * @param message the message from which to retrieve the accessor
     * @return the {@link StompHeaderAccessor} instance
     * @since 1.0
     */
    public StompHeaderAccessor getAccessor(Message<?> message) {
        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    }
}
