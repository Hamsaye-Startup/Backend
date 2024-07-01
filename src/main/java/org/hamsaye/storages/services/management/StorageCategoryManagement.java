package org.hamsaye.storages.services.management;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.requests.StorageCategoryRequest;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.mappers.StorageCategoryMapper;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.services.reader.StorageCategoryServiceReader;
import org.hamsaye.storages.services.writer.StorageCategoryServiceWriter;
import org.hamsaye.utils.log.Functional;
import org.hamsaye.utils.log.Functionality;
import org.hamsaye.storages.wrapper.StorageCategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageCategoryManagement {

    private StorageCategoryServiceReader storageCategoryServiceReader;
    private StorageCategoryServiceWriter storageCategoryServiceWriter;
    private StorageCategoryMapper mapper;
    private StorageCategoryWrapper wrapper;

    @Autowired
    public StorageCategoryManagement(StorageCategoryServiceReader storageCategoryServiceReader, StorageCategoryServiceWriter storageCategoryServiceWriter,
                                     StorageCategoryMapper mapper, StorageCategoryWrapper wrapper) {
        this.storageCategoryServiceReader = storageCategoryServiceReader;
        this.storageCategoryServiceWriter = storageCategoryServiceWriter;
        this.mapper = mapper;
        this.wrapper = wrapper;
    }

    /*
     * Add new Storage Category
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Functionality> addStorageCategory(StorageCategoryRequest request) {

        // generate a functional instance
        Functional functional = new Functional();

        // convert request to storage category entity
        StorageCategoryEntity category = mapper.storageCategoryRequestToStorageCategory(request);

        /*
         * apply all activities (operations):
         * insert new storage category
         *
         * */
        Map<String, Functionality> results =
                functional.apply("category", storageCategoryServiceWriter::persistAndFlush, category)
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
    public Map<String, Functionality> updateStorageCategory(StorageCategoryRequest request) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteStorage(String code) {

        // find storage category by uid
        StorageCategoryEntity category = storageCategoryServiceReader.findByCode(code);

        // delete the storage category
        storageCategoryServiceWriter.delete(category);
    }

    /*
    * Find Category by Code (example: "AE45B")
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StorageCategoryResponse findStorageCategoryByCode(String code) {
        // find the category by code
        StorageCategoryEntity category = storageCategoryServiceReader.findByCode(code);

        // convert category entity to response
        return mapper.storageCategoryToStorageCategoryResponse(category);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<StorageCategoryResponse> findAllStorageCategories() {

        // find all storage categories and convert them to response
        return storageCategoryServiceReader.findAll()
                .stream()
                .map(mapper::storageCategoryToStorageCategoryResponse)
                .collect(Collectors.toList());
    }
}
