package com.hamsaye.chat.kafka.models;

import com.hamsaye.chat.users.models.UserLoyaltyStatus;
import lombok.Builder;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing customer information for Kafka messages.
 * <p>
 * This record holds the details of a customer including unique identifier, gender, and loyalty status.
 * It is used for transferring customer data across different components of the system, especially in
 * Kafka message contexts.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CustomerDTO(
        UUID uid,
        GenderEnum genderEnum,

        /**
         * Loyalty status of the customer.
         */
        UserLoyaltyStatus loyaltyStatus
) {
}
