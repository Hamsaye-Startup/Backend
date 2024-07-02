package org.hamsaye.geo.services.management;


import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.dtos.requests.CityRequest;
import org.hamsaye.geo.dtos.responses.CityResponse;
import org.hamsaye.geo.mappers.CityMapper;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.geo.services.reader.CityServiceReader;
import org.hamsaye.geo.services.writer.CityServiceWriter;
import org.hamsaye.geo.wrappers.CityWrapper;
import org.hamsaye.utils.functional.Functional;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceManagement {

    private CityServiceWriter cityServiceWriter;
    private CityServiceReader cityServiceReader;
    private CityMapper mapper;
    private CityWrapper wrapper;

    @Autowired
    public CityServiceManagement(CityServiceWriter cityServiceWriter,
                                 CityServiceReader cityServiceReader,
                                 CityMapper mapper,
                                 CityWrapper wrapper) {
        this.cityServiceWriter = cityServiceWriter;
        this.cityServiceReader = cityServiceReader;
        this.mapper = mapper;
        this.wrapper = wrapper;
    }


    /*
     * Add new City
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> addCity(CityRequest request) {

        // generate a functional instance
        Functional functional = new Functional();

        // convert request to city entity
        CityEntity city = mapper.cityRequestToCity(request);

        /*
         * apply all activities (operations):
         * insert new city
         * */

        Map<String, Functionality> results =
                functional.apply("storage", cityServiceWriter::persistAndFlush, city)
                        .getResults();

        // convert entities to response
        results.replaceAll((k, v) -> wrapper.typeOf(v));

        return results;
    }

    /*
     * Update the city
     * It offers updating city for admin and post-admin
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> updateCity(CityRequest request) {
        return null;
    }

    /*
     * Delete the city by high permission
     * Note: Only admin and post-admin can use this method for deleting storages
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCity(UUID uid) {

        // find city by uid
        CityEntity city = cityServiceReader.findById(uid);

        // delete the city
        cityServiceWriter.delete(city);
    }

    /*
     * Find by uid method occurred externally
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CityResponse findCityById(UUID uid) {

        // find city by uid
        CityEntity city = cityServiceReader.findById(uid);

        // convert city entity to response
        return mapper.cityToCityResponse(city);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CityResponse> findAllCities() {

        // find all cities and convert them to response
        return cityServiceReader.findAll()
                .stream()
                .map(mapper::cityToCityResponse)
                .collect(Collectors.toList());
    }
}
