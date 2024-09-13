package com.microservices.user.users.responses;

import com.microservices.user.users.models.GenderEnum;
import com.microservices.user.users.models.UserConnectionStatus;
import com.microservices.user.users.models.UserLoyaltyStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a response containing details about a user detail.
 * This record includes essential user information such as their unique identifier, user details,
 * creation timestamp, national code, and biography.
 *
 * @param uid        the unique identifier of the user.
 * @param createdAt  the timestamp when the customer was created.
 * @param nid        the national identification code of the user.
 * @param bio        the biography of the user.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDetailResponse(
        UUID uid,
        String nid,
        String bio,
        LocalDateTime createdAt,
        GenderEnum gender,
        UserLoyaltyStatus loyalty,
        UserConnectionStatus connection

        // TODO: profile image (consider adding a field for the image URL or path)
) {
}
