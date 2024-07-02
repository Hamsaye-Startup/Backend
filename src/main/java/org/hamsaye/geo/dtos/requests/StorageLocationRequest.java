package org.hamsaye.geo.dtos.requests;

import lombok.Builder;
import org.hamsaye.geo.dtos.responses.CityResponse;
import org.hamsaye.storages.dtos.responses.StorageResponse;

@Builder
public record StorageLocationRequest(
        String address,
        String gp,
        StorageResponse storage,
        CityResponse city
) {}
