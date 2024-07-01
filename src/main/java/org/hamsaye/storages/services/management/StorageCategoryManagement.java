package org.hamsaye.storages.services.management;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.dtos.responses.StorageCategoryResponse;
import org.hamsaye.storages.mappers.StorageCategoryMapper;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.services.reader.StorageCategoryServiceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StorageCategoryManagement {

    private StorageCategoryServiceReader storageCategoryServiceReader;
    private StorageCategoryMapper mapper;

    @Autowired
    public StorageCategoryManagement(StorageCategoryServiceReader storageCategoryServiceReader,
                                     StorageCategoryMapper mapper) {
        this.storageCategoryServiceReader = storageCategoryServiceReader;
        this.mapper = mapper;
    }

    /*
    *
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StorageCategoryResponse findCategoryByCode(String code) {
        // find the category by code
        StorageCategoryEntity category = storageCategoryServiceReader.findByCode(code);

        // convert category entity to response
        return mapper.storageCategoryToStorageCategoryResponse(category);
    }
}
