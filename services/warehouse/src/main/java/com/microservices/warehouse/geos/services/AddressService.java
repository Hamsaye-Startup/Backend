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

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    // generate address details entity by generateAddressDetails function
    public AddressEntity generateAddress(AddressRequests addressRequests) {
        return AddressEntity.builder()
                .address(addressRequests.address())
                .addressCompat(addressRequests.addressCompat())
                .coordinate(addressRequests.lat() + "," + addressRequests.lon())
                .details(generateAddressDetails(addressRequests.postalCode(), addressRequests))
                .build();
    }

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
