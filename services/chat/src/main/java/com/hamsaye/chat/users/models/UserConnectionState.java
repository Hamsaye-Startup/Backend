package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

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
