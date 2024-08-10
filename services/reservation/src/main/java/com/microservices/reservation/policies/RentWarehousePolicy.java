package com.microservices.reservation.policies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentWarehousePolicy {

    @Value("${policies.warehouse.site-fee-percent:0.1}")
    public static float siteFeePercent;
}
