package org.hamsaye.geo.services.management;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.dtos.requests.StorageLocationRequest;
import org.hamsaye.geo.dtos.responses.StorageLocationResponse;
import org.hamsaye.geo.mappers.StorageLocationMapper;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.geo.services.facade.LocationComponentsResearcher;
import org.hamsaye.geo.services.reader.StorageLocationServiceReader;
import org.hamsaye.geo.services.writer.StorageLocationServiceWriter;
import org.hamsaye.geo.wrappers.StorageLocationWrapper;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.utils.functional.Functional;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageLocationManagement {

    private StorageLocationServiceWriter storageLocationServiceWriter;
    private StorageLocationServiceReader storageLocationServiceReader;
    private StorageLocationMapper mapper;
    private StorageLocationWrapper wrapper;

    private LocationComponentsResearcher researcher;

    @Autowired
    public StorageLocationManagement(StorageLocationServiceWriter storageLocationServiceWriter,
                                     StorageLocationServiceReader storageLocationServiceReader,
                                     StorageLocationMapper mapper,
                                     StorageLocationWrapper wrapper,
                                     LocationComponentsResearcher researcher) {
        this.storageLocationServiceWriter = storageLocationServiceWriter;
        this.storageLocationServiceReader = storageLocationServiceReader;
        this.mapper = mapper;
        this.wrapper = wrapper;
        this.researcher = researcher;
    }

    /*
     * Add new Storage Location
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> addStorageLocation(StorageLocationRequest request) {

        // generate a functional instance
        Functional functional = new Functional();

        // convert request to storage location entity
        StorageLocationEntity storageLocation = mapper.storageLocationRequestToStorageLocation(request);

        // find storage from storage dataset
        StorageEntity storage = researcher.findStorageLocation(storageLocation.getStorage().getUid());

        // find city from geo dataset
        CityEntity city = researcher.findCity(storageLocation.getCity().getUid());

        // set the new parameters
        storageLocation.setStorage(storage);
        storageLocation.setCity(city);

        /*
         * apply all activities (operations):
         * insert new storage location
         * */

        Map<String, Functionality> results =
                functional.apply("storage", storageLocationServiceWriter::persistAndFlush, storageLocation)
                        .getResults();

        // convert entities to response
        results.replaceAll((k, v) -> wrapper.typeOf(v));

        return results;
    }

    /*
     * Update the storage location
     * It offers updating city for admin and post-admin
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> updateStorageLocation(StorageLocationRequest request) {
        return null;
    }

    /*
     * Delete the storage location by high permission
     * Note: Only admin and post-admin can use this method for deleting storages
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStorageLocation(Long id) {

        // find storage location by id
        StorageLocationEntity storageLocation = storageLocationServiceReader.findById(id);

        // delete the storage location
        storageLocationServiceWriter.delete(storageLocation);
    }

    /*
     * Find by uid method occurred externally
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StorageLocationResponse findStorageLocationById(Long id) {

        // find storage location by id
        StorageLocationEntity storageLocation = storageLocationServiceReader.findById(id);

        // convert storage location entity to response
        return mapper.storageLocationToStorageLocationResponse(storageLocation);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<StorageLocationResponse> findAllStorageLocations() {

        // find all storage locations and convert them to response
        return storageLocationServiceReader.findAll()
                .stream()
                .map(mapper::storageLocationToStorageLocationResponse)
                .collect(Collectors.toList());
    }
}
