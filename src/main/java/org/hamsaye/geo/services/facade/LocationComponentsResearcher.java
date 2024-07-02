package org.hamsaye.geo.services.facade;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.geo.services.reader.CityServiceReader;
import org.hamsaye.geo.services.reader.StorageLocationServiceReader;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.storages.services.reader.StorageServiceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationComponentsResearcher {

    private CityServiceReader cityServiceReader;
    private StorageServiceReader storageServiceReader;

    @Autowired
    public LocationComponentsResearcher(CityServiceReader cityServiceReader,
                                        StorageServiceReader storageServiceReader) {
        this.cityServiceReader = cityServiceReader;
        this.storageServiceReader = storageServiceReader;
    }

    public CityEntity findCity(UUID uid) {
        return cityServiceReader.findById(uid);
    }

    public StorageEntity findStorageLocation(UUID uid) {
        return storageServiceReader.findById(uid);
    }
}
