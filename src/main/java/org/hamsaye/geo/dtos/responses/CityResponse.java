package org.hamsaye.geo.dtos.responses;

import lombok.Builder;
import org.hamsaye.utils.functional.Functionality;

import java.util.UUID;

@Builder
public record CityResponse(
        UUID uid,
        String name,
        String abbreviation,
        Boolean active
) implements Functionality {}
