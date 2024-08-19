package com.microservices.user.customers.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerDTO(
        UUID uid,
        GenderEnum genderEnum,
        UserLoyaltyStatus loyaltyStatus
) {
}
