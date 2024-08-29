package com.microservices.warehouse.geos.requests;

import lombok.Builder;

@Builder
public record AddressRequests(
        String address,
        String addressCompat,
        String lat,
        String lon,
        String postalCode,
        String country,
        String province,
        String county,
        String city,
        String region,
        String neighborhood,
        String primary,
        String plaque
) {
}