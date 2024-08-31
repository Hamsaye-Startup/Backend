package com.microservices.user.customers.dto;

import com.microservices.user.customers.models.GenderEnum;
import com.microservices.user.customers.models.UserLoyaltyStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerDTO(
        UUID uid,
        GenderEnum genderEnum,
        UserLoyaltyStatus loyaltyStatus
) {
}
