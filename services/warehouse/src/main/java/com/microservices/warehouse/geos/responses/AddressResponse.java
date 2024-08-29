package com.microservices.warehouse.geos.responses;

import lombok.Builder;

@Builder
public record AddressResponse(

        String address,
        String addressCompat,
        String lat,
        String lon,
        PoliticalDivision politicalDivision,
        Locality locality

) {
}
