package com.microservices.warehouse.geos.mappers;

import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.responses.AddressResponse;
import com.microservices.warehouse.geos.responses.Locality;
import com.microservices.warehouse.geos.responses.PoliticalDivision;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public AddressResponse toResponse(AddressEntity address) {

        // convert string to array
        String[] coordinate = address.getCoordinate().split(";");

        return AddressResponse.builder()
                .address(address.getAddress())
                .addressCompat(address.getAddressCompat())
                .lat(coordinate[0])
                .lon(coordinate[1])
                .politicalDivision(toPoliticalDivision(address.getDetails()))
                .locality(toLocality(address.getDetails()))
                .build();
    }

    private PoliticalDivision toPoliticalDivision(AddressDetailsEntity address) {
        return PoliticalDivision.builder()
                .country(address.getCountry())
                .province(address.getProvince())
                .county(address.getCounty())
                .build();
    }

    private Locality toLocality(AddressDetailsEntity address) {
        return Locality.builder()
                .region(address.getRegion())
                .city(address.getCity())
                .neighborhood(address.getNeighborhood())
                .primary(address.getPrimary())
                .plaque(address.getPlaque())
                .build();
    }
}
