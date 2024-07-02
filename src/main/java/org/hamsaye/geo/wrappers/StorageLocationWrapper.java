package org.hamsaye.geo.wrappers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.mappers.CityMapper;
import org.hamsaye.geo.mappers.StorageLocationMapper;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.utils.functional.Functionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageLocationWrapper {

    private StorageLocationMapper mapper;

    @Autowired
    public StorageLocationWrapper(StorageLocationMapper mapper) {
        this.mapper = mapper;
    }

    /*
    *
    * preparing the data for sending by casting them to their response type
    * to make sure we should check the type of class by this wrapper class.
    *
    * */
    public <Result extends Functionality> Result typeOf(Functionality object) {
        if (object instanceof StorageLocationEntity) {
            Result result = (Result) mapper.storageLocationToStorageLocationResponse((StorageLocationEntity) object);
            return result;
        }
        throw new RuntimeException("unknown type is recognized");
    }
}
