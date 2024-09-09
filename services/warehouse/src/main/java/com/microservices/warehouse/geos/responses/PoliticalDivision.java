package com.microservices.warehouse.geos.responses;

import lombok.Builder;

/**
 * Represents the political division details of an address.
 * <p>
 * This record encapsulates the hierarchical political divisions including country, province, and county.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record PoliticalDivision(

        /**
         * The country of the political division.
         */
        String country,

        /**
         * The province or state within the country.
         */
        String province,

        /**
         * The county or district within the province or state.
         */
        String county

) {
}
