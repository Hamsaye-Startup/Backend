package org.hamsaye.storages.services.management;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageFeatureRequest;
import org.hamsaye.storages.dtos.responses.StorageFeatureResponse;
import org.hamsaye.storages.mappers.StorageFeatureMapper;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.storages.services.reader.StorageFeatureServiceReader;
import org.hamsaye.storages.services.writer.StorageFeatureServiceWriter;
import org.hamsaye.storages.wrapper.StorageFeatureWrapper;
import org.hamsaye.utils.log.Functional;
import org.hamsaye.utils.log.Functionality;
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
public class StorageFeatureManagement {

    private StorageFeatureServiceWriter storageFeatureServiceWriter;
    private StorageFeatureServiceReader storageFeatureServiceReader;
    private StorageFeatureMapper mapper;

    private StorageFeatureWrapper wrapper;

    @Autowired
    public StorageFeatureManagement(StorageFeatureServiceWriter storageFeatureServiceWriter,
                                    StorageFeatureServiceReader storageFeatureServiceReader,
                                    StorageFeatureMapper mapper, StorageFeatureWrapper wrapper) {
        this.storageFeatureServiceWriter = storageFeatureServiceWriter;
        this.storageFeatureServiceReader = storageFeatureServiceReader;
        this.mapper = mapper;
        this.wrapper = wrapper;
    }

    /*
     * Add new Storage Feature
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> addStorageFeature(StorageFeatureRequest request) {

        // generate a functional instance
        Functional functional = new Functional();

        // convert request to storage feature entity
        StorageFeatureEntity feature = mapper.storageFeatureRequestToStorageFeature(request);

        /*
         * apply all activities (operations):
         * insert new storage feature
         *
         * */
        Map<String, Functionality> results =
                functional.apply("feature", storageFeatureServiceWriter::persistAndFlush, feature)
                        .getResults();

        // convert entities to response
        results.replaceAll((k, v) -> wrapper.typeOf(v));

        return results;
    }

    /*
     * Update the storage
     * It offers updating storage for normal users
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> updateStorageFeature(StorageFeatureRequest request) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStorage(UUID uid) {

        // find storage feature by uid
        StorageFeatureEntity feature = storageFeatureServiceReader.findById(uid);

        // delete the storage feature
        storageFeatureServiceWriter.delete(feature);
    }

    /*
     * Find Feature by UUID
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StorageFeatureResponse findStorageFeatureById(UUID uid) {
        // find the feature by code
        StorageFeatureEntity feature = storageFeatureServiceReader.findById(uid);

        // convert feature entity to response
        return mapper.storageFeatureToStorageFeatureResponse(feature);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<StorageFeatureResponse> findAllStorageFeatures() {

        // find all storage categories and convert them to response
        return storageFeatureServiceReader.findAll()
                .stream()
                .map(mapper::storageFeatureToStorageFeatureResponse)
                .collect(Collectors.toList());
    }
}
