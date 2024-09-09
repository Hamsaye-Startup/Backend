package com.microservices.user.customers.requests;

import com.microservices.user.customers.dto.CustomerDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents a request for notifying about a customer event.
 * Contains the details of the customer, the message to be sent,
 * and the type of notification.
 *
 * @param customerInfo the {@link CustomerDTO} containing information about the customer.
 * @param message      the notification message.
 * @param type         the type of notification.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CustomerNotifyRequest(
        CustomerDTO customerInfo,
        String message,
        CustomerNotifyType type
) implements Serializable {
}
