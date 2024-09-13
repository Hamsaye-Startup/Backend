package com.microservices.user.users.dto;

import com.microservices.user.users.models.GenderEnum;
import com.microservices.user.users.models.UserLoyaltyStatus;
import lombok.Builder;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDetailDTO(
        UUID uid,
        GenderEnum gender,
        UserLoyaltyStatus loyalty
) {
}
