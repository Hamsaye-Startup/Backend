package org.hamsaye.geo.mappers;

import org.hamsaye.geo.dtos.requests.CityRequest;
import org.hamsaye.geo.dtos.responses.CityResponse;
import org.hamsaye.geo.exceptions.CityMapperException;
import org.hamsaye.geo.models.CityEntity;
import org.springframework.stereotype.Service;

@Service
public class CityMapper {

    public CityResponse cityToCityResponse(CityEntity city) throws CityMapperException {
        try {
            return CityResponse.builder()
                    .uid(city.getUid())
                    .name(city.getName())
                    .abbreviation(city.getAbbreviation())
                    .active(city.getActive())
                    .build();
        } catch (RuntimeException ex) {
            throw new CityMapperException();
        }
    }

    public CityEntity cityResponseToCity(CityResponse response) throws CityMapperException {
        try {
            return CityEntity.builder()
                    .uid(response.uid())
                    .name(response.name())
                    .abbreviation(response.abbreviation())
                    .active(response.active())
                    .build();
        } catch (RuntimeException ex) {
            throw new CityMapperException();
        }
    }

    public CityEntity cityRequestToCity(CityRequest request) throws CityMapperException {
        try {
            return CityEntity.builder()
                    .name(request.name())
                    .abbreviation(request.abbreviation())
                    .active(request.active())
                    .build();
        } catch (RuntimeException ex) {
            throw new CityMapperException();
        }
    }
}
