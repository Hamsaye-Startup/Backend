package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Represents the state of a user's connection in the chat system.
 * <p>
 * This record holds details about the user's WebSocket connection, including the socket ID,
 * the time the connection was initiated, and the current connection status.
 * </p>
 *
 * @param socketId         The unique identifier of the WebSocket connection.
 * @param initiatedAt      The timestamp when the connection was initiated.
 * @param connectionStatus The current status of the user's connection, represented by {@link ConnectionStatus}.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserConnectionState(
        @Field("socket_id")
        String socketId,
        @Field("initiated_at")
        LocalDateTime initiatedAt,
        @Field("connection_status")
        ConnectionStatus connectionStatus
) {
}
