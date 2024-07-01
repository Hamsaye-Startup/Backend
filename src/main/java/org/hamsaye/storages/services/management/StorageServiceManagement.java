package org.hamsaye.storages.services.management;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageRequest;
import org.hamsaye.storages.dtos.responses.StorageResponse;
import org.hamsaye.storages.mappers.StorageMapper;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.services.reader.StorageServiceReader;
import org.hamsaye.storages.services.writer.StorageServiceWriter;
import org.hamsaye.storages.status.StorageStatus;
import org.hamsaye.storages.status.StorageStatusGenerator;
import org.hamsaye.utils.log.Functionality;
import org.hamsaye.utils.log.Functional;
import org.hamsaye.utils.wrapper.StorageWrapper;
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
public class StorageServiceManagement {

    private StorageServiceReader storageServiceReader;
    private StorageServiceWriter storageServiceWriter;
    private StorageMapper mapper;

    private StorageWrapper wrapper;

    @Autowired
    public StorageServiceManagement(StorageServiceReader storageServiceReader,
                                    StorageServiceWriter storageServiceWriter,
                                    StorageMapper mapper, StorageWrapper wrapper) {
        this.storageServiceReader = storageServiceReader;
        this.storageServiceWriter = storageServiceWriter;
        this.mapper = mapper;
        this.wrapper = wrapper;
    }

    /*
    * Add new Storage
    * Conditions as follows:
    * 1.it's not verified
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> addStorage(StorageRequest request) {

        // generate a functional instance
        Functional functional = new Functional();

        // convert request to storage entity
        StorageEntity storage = mapper.storageRequestToStorage(request);

        // generate Storage Status
        StorageStatus status = StorageStatusGenerator.generateNewStorage();

        // set status
        storage.setStatus(mapper.storageStatusToString(status));

        /*
        * apply all activities (operations):
        * insert new storage
        *
        * */
        Map<String, Functionality> results =
                functional.apply("storage", storageServiceWriter::persistAndFlush, storage)
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
    public Map<String, Functionality> updateStorage(StorageRequest request) {
        return null;
    }

    /*
     * Update the storage
     * It offers updating storage for internal systems
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> updateStorage(StorageRequest request, StorageStatus status) {
        return null;
    }

    /*
    * Delete the storage by high permission
    * Note: Only admin and post-admin can use this method for deleting storages
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStorage(UUID uid) {

        // find storage by uid
        StorageEntity storage = storageServiceReader.findById(uid);

        // delete the storage
        storageServiceWriter.delete(storage);
    }

    /*
    * Find by uid method occurred externally
    * Note: The normal users must see yourself
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StorageResponse findStorageById(UUID uid) {

        // find storage by uid
        StorageEntity storage = storageServiceReader.findById(uid);

        // convert storage entity to response
        return mapper.storageToStorageResponse(storage);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<StorageResponse> findAllStorages() {

        // find all storages and convert them to response
        return storageServiceReader.findAll()
                .stream()
                .map(mapper::storageToStorageResponse)
                .collect(Collectors.toList());
    }

}
