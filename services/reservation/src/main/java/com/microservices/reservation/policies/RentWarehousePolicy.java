package com.microservices.reservation.policies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for rental policies related to reserving storage.
 * This class holds configuration values that define the policies applied during warehouse reservations.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
public class RentWarehousePolicy {

    /**
     * The percentage of the site fee applied during a warehouse reservation.
     * This value is read from the application properties and defaults to 0.1 (10%) if not specified.
     */
    @Value("${policies.warehouse.site-fee-percent:0.1}")
    public static float siteFeePercent;
}
