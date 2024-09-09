package com.microservices.warehouse.geos.responses;

import lombok.Builder;

/**
 * Represents the detailed address response containing all relevant address components.
 * <p>
 * This record provides comprehensive information about an address including its base address,
 * compatibility address, geographic coordinates, and hierarchical political and locality divisions.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record AddressResponse(

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
         * The political division details of the address.
         * See {@link com.microservices.warehouse.geos.responses.PoliticalDivision} for more details.
         */
        PoliticalDivision politicalDivision,

        /**
         * The locality details of the address.
         * See {@link com.microservices.warehouse.geos.responses.Locality} for more details.
         */
        Locality locality

) {
}
