package com.hamsaye.chat.kafka.requests;

import com.hamsaye.chat.kafka.models.CustomerDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents a notification request for customer information sent over Kafka.
 * <p>
 * This record encapsulates the details about customer notifications, including the customer
 * information, an optional message, and the type of notification.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CustomerNotifyRequest(

        /**
         * The customer information to be included in the notification.
         * <p>
         * This field contains details such as the customer's ID, gender, and loyalty status.
         * </p>
         */
        CustomerDTO customerInfo,

        /**
         * An optional message associated with the notification.
         * <p>
         * This field can be used to provide additional context or details about the notification.
         * </p>
         */
        String message,

        /**
         * The type of customer notification.
         * <p>
         * This field specifies the kind of notification being sent, such as a new customer or an
         * update to customer information.
         * </p>
         */
        CustomerNotifyType type

) implements Serializable {
}
