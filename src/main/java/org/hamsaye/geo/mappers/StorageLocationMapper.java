package org.hamsaye.geo.mappers;

import lombok.RequiredArgsConstructor;
import org.hamsaye.geo.dtos.requests.StorageLocationRequest;
import org.hamsaye.geo.dtos.responses.StorageLocationResponse;
import org.hamsaye.geo.exceptions.StorageLocationMapperException;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.storages.mappers.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageLocationMapper {

    private StorageMapper storageMapper;
    private CityMapper cityMapper;

    @Autowired
    public StorageLocationMapper(StorageMapper storageMapper, CityMapper cityMapper) {
        this.storageMapper = storageMapper;
        this.cityMapper = cityMapper;
    }

    public StorageLocationResponse storageLocationToStorageLocationResponse(StorageLocationEntity location) throws StorageLocationMapperException {
        try {
            return StorageLocationResponse.builder()
                    .id(location.getId())
                    .address(location.getAddress())
                    .gp(location.getGp())
                    .storage(storageMapper.storageToStorageResponse(location.getStorage()))
                    .city(cityMapper.cityToCityResponse(location.getCity()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageLocationMapperException();
        }
    }

    public StorageLocationEntity storageLocationResponseToStorageLocation(StorageLocationResponse response) throws StorageLocationMapperException {
        try {
            return StorageLocationEntity.builder()
                    .id(response.id())
                    .address(response.address())
                    .gp(response.gp())
                    .storage(storageMapper.storageResponseToStorage(response.storage()))
                    .city(cityMapper.cityResponseToCity(response.city()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageLocationMapperException();
        }
    }

    public StorageLocationEntity storageLocationRequestToStorageLocation(StorageLocationRequest request) throws StorageLocationMapperException {
        try {
            return StorageLocationEntity.builder()
                    .address(request.address())
                    .gp(request.gp())
                    .storage(storageMapper.storageResponseToStorage(request.storage()))
                    .city(cityMapper.cityResponseToCity(request.city()))
                    .build();
        } catch (RuntimeException ex) {
            throw new StorageLocationMapperException();
        }
    }
}
