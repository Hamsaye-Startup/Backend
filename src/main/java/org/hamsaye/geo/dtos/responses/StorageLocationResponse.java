package org.hamsaye.geo.dtos.responses;

import lombok.Builder;
import org.hamsaye.storages.dtos.responses.StorageResponse;
import org.hamsaye.utils.functional.Functionality;

@Builder
public record StorageLocationResponse(
        Long id,
        String address,
        String gp,
        StorageResponse storage,
        CityResponse city

) implements Functionality {}
