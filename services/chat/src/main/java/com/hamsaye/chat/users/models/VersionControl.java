package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record VersionControl(
        @Field("app_version")
        String appVersion
) {
}
