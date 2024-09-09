package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A record representing version control details of the application.
 * <p>
 * This record is primarily used to track the version of the application
 * or system being used by the user or system.
 * </p>
 *
 * @param appVersion the current version of the application
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record VersionControl(
        @Field("app_version")
        String appVersion
) {
}
