package com.microservices.warehouse.geos.services;

import com.microservices.warehouse.geos.models.AddressDetailsEntity;
import com.microservices.warehouse.geos.models.AddressEntity;
import com.microservices.warehouse.geos.repositories.AddressRepository;
import com.microservices.warehouse.geos.requests.AddressRequests;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling address-related operations.
 * <p>
 * This service provides methods for generating address entities from requests,
 * generating address details, and removing address entities from the repository.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AddressService {

    /**
     * Repository for managing address entities.
     * See {@link com.microservices.warehouse.geos.repositories.AddressRepository} for more details.
     */
    private final AddressRepository addressRepository;

    /**
     * Generates an {@link AddressEntity} from the given {@link AddressRequests}.
     *
     * <p>
     * This method creates an address entity with the provided address information and
     * generates additional details such as coordinates and address details.
     * </p>
     *
     * @param addressRequests the address requests containing address information
     * @return the generated address entity
     * @since 1.0
     */
    public AddressEntity generateAddress(AddressRequests addressRequests) {
        return AddressEntity.builder()
                .address(addressRequests.address())
                .addressCompat(addressRequests.addressCompat())
                .coordinate(addressRequests.lat() + ";" + addressRequests.lon())
                .details(generateAddressDetails(addressRequests.postalCode(), addressRequests))
                .build();
    }

    /**
     * Generates an {@link AddressDetailsEntity} based on the provided postal code and address requests.
     *
     * @param postalCode the postal code of the address
     * @param addressRequests the address requests containing address details
     * @return the generated address details entity
     * @since 1.0
     */
    private AddressDetailsEntity generateAddressDetails(
            @NonNull String postalCode,
            AddressRequests addressRequests
    ) {
        return AddressDetailsEntity.builder()
                .postalCode(postalCode)
                .country(addressRequests.country())
                .province(addressRequests.province())
                .county(addressRequests.county())
                .city(addressRequests.city())
                .region(addressRequests.region())
                .neighborhood(addressRequests.neighborhood())
                .primary(addressRequests.primary())
                .plaque(addressRequests.plaque())
                .build();
    }

    /**
     * Removes the given {@link AddressEntity} from the repository.
     *
     * <p>
     * This method attempts to delete the address entity from the repository and returns
     * true if the operation was successful, or false if an exception occurred.
     * </p>
     *
     * @param address the address entity to be removed
     * @return true if the address was successfully removed, false otherwise
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(AddressEntity address) {
        try {
            addressRepository.delete(address);
            return true;
        }
        catch (RuntimeException ex) {
            return false;
        }
    }
}
