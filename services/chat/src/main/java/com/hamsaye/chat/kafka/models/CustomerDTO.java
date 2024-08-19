package com.hamsaye.chat.kafka.models;

import com.hamsaye.chat.users.models.UserLoyaltyStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerDTO(
        UUID uid,
        GenderEnum genderEnum,
        UserLoyaltyStatus loyaltyStatus
) {
}
