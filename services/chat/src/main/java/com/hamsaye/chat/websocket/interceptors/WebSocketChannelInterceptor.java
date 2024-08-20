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

@Service
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    // private static final String SESSION_ID_HEADER = "SESSION_ID";
    // private static final String API_KEY_HEADER = "API_KEY";
    private static final String USER_ID_HEADER = "X_USER_ID";

    /*
    * process the message before sending
    * */

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

    public StompHeaderAccessor getAccessor(Message<?> message) {
        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    }
}
