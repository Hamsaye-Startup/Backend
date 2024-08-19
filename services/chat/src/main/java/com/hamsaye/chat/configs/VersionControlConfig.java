package com.hamsaye.chat.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record VersionControlConfig(
        @Value("${hamsaye.chat.version:1.0.0}")
        String appVersion
) {
}
