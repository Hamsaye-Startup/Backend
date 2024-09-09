package com.microservices.warehouse.geos.mappers;

import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.responses.AddressResponse;
import com.microservices.warehouse.geos.responses.Locality;
import com.microservices.warehouse.geos.responses.PoliticalDivision;
import org.springframework.stereotype.Service;

/**
 * A service class responsible for mapping {@link AddressEntity} objects to {@link AddressResponse} objects.
 * <p>
 * This class contains methods to convert address entities and their details into response objects used
 * in the API layer. It handles the transformation of address data to a structured format suitable for
 * client responses.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class AddressMapper {

    /**
     * Converts an {@link AddressEntity} to an {@link AddressResponse}.
     *
     * @param address the address entity to convert
     * @return the corresponding address response
     * @since 1.0
     */
    public AddressResponse toResponse(AddressEntity address) {

        // Convert coordinate string to array
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

    /**
     * Maps an {@link AddressDetailsEntity} to a {@link PoliticalDivision} object.
     *
     * @param address the address details entity to map
     * @return the corresponding political division object
     * @since 1.0
     */
    private PoliticalDivision toPoliticalDivision(AddressDetailsEntity address) {
        return PoliticalDivision.builder()
                .country(address.getCountry())
                .province(address.getProvince())
                .county(address.getCounty())
                .build();
    }

    /**
     * Maps an {@link AddressDetailsEntity} to a {@link Locality} object.
     *
     * @param address the address details entity to map
     * @return the corresponding locality object
     * @since 1.0
     */
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
