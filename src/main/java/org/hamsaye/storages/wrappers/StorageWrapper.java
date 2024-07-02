package org.hamsaye.storages.wrappers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.mappers.StorageMapper;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageWrapper {

    private StorageMapper mapper;

    @Autowired
    public StorageWrapper(StorageMapper mapper) {
        this.mapper = mapper;
    }

    /*
    *
    * preparing the data for sending by casting them to their response type
    * to make sure we should check the type of class by this wrapper class.
    *
    * */
    public <Result extends Functionality> Result typeOf(Functionality object) {
        if (object instanceof StorageEntity) {
            Result result = (Result) mapper.storageToStorageResponse((StorageEntity) object);
            return result;
        }
        throw new RuntimeException("unknown type is recognized");
    }
}
