package com.microservices.warehouse.geos.responses;

import lombok.Builder;

/**
 * Represents the locality details of an address.
 * <p>
 * This record encapsulates various locality aspects including city, region, neighborhood, primary address, and plaque.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record Locality(

        /**
         * The city of the locality.
         */
        String city,

        /**
         * The region within the city.
         */
        String region,

        /**
         * The neighborhood within the city or region.
         */
        String neighborhood,

        /**
         * The primary street of the locality.
         */
        String primary,

        /**
         * Additional plaque or identifier for the locality.
         */
        String plaque

) {
}
