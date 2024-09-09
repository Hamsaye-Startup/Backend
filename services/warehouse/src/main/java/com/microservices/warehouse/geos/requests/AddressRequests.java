package com.microservices.warehouse.geos.requests;

import lombok.Builder;

/**
 * Represents a request for address details.
 * <p>
 * This record is used to encapsulate the data required for creating or updating an address,
 * including the base address, geographic coordinates, postal code, and various levels of
 * political and locality divisions.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record AddressRequests(

        /**
         * The main address line.
         */
        String address,

        /**
         * The address in a compatible format.
         */
        String addressCompat,

        /**
         * The latitude coordinate of the address.
         */
        String lat,

        /**
         * The longitude coordinate of the address.
         */
        String lon,

        /**
         * The postal code for the address.
         */
        String postalCode,

        /**
         * The country of the address.
         */
        String country,

        /**
         * The province or state of the address.
         */
        String province,

        /**
         * The county or district of the address.
         */
        String county,

        /**
         * The city of the address.
         */
        String city,

        /**
         * The region of the address.
         */
        String region,

        /**
         * The neighborhood of the address.
         */
        String neighborhood,

        /**
         * The primary street of the address.
         */
        String primary,

        /**
         * The plaque or specific number associated with the address.
         */
        String plaque

) {
}
