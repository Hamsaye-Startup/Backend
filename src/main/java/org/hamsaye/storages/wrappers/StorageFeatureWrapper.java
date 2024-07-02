package org.hamsaye.storages.wrappers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.mappers.StorageFeatureMapper;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageFeatureWrapper {

    private StorageFeatureMapper mapper;

    @Autowired
    public StorageFeatureWrapper(StorageFeatureMapper mapper) {
        this.mapper = mapper;
    }

    /*
     *
     * preparing the data for sending by casting them to their response type
     * to make sure we should check the type of class by this wrapper class.
     *
     * */
    public <Result extends Functionality> Result typeOf(Functionality object) {
        if (object instanceof StorageFeatureEntity) {
            Result result = (Result) mapper.storageFeatureToStorageFeatureResponse((StorageFeatureEntity) object);
            return result;
        }
        throw new RuntimeException("unknown type is recognized");
    }
}
