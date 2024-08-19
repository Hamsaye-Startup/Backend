package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Builder
public record UserRef(
        UUID uid,
        String name
) {
}
