package com.microservices.warehouse.warehouses.services;

import com.microservices.warehouse.warehouses.models.WarehouseEntity;
import com.microservices.warehouse.warehouses.repositories.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseStatusService {

    private final BookmarkRepository bookmarkRepository;

    public WarehouseEntity isMarked(WarehouseEntity warehouse, Principal principal) {
        // check the principal is not null
        if (principal == null) {
            throw new NullPointerException("The authentication is required :: principal is null");
        }

        // find the bookmark row
        boolean exists = bookmarkRepository.existByIdAndWarehouseId(
                UUID.fromString(principal.getName()),
                warehouse.getId()
        );

        warehouse.setMarked(exists);
        return warehouse;
    }
}
