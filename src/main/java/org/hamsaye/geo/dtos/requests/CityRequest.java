package org.hamsaye.geo.dtos.requests;

import lombok.Builder;

@Builder
public record CityRequest(
        String name,
        String abbreviation,
        Boolean active
) {}
