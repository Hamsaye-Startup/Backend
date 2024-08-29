package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.applications.exceptions.IllegalOperationException;
import com.microservices.warehouse.storages.mappers.FeatureMapper;
import com.microservices.warehouse.storages.mappers.StorageMapper;
import com.microservices.warehouse.storages.models.FeatureEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeatureServiceManagement {

    private final FeatureMapper featureMapper;
    private final FeatureJDBCService featureJDBCService;

    private final StorageService storageService;
    private final StorageMapper storageMapper;

    public FeatureDTO insertFeature(FeatureDTO dto) {

        // convert the dto to entity
        FeatureEntity feature = featureMapper.toFeature(dto);
        return featureMapper.toDto(featureJDBCService.insertFeature(feature));
    }

    /*
    * update the feature's code, title and description
    * */
    public FeatureDTO updateFeature(FeatureDTO dto) {

        // find the feature by code
        FeatureEntity existed = featureJDBCService.findFeatureByCode(dto.code());

        // convert the dto to entity
        FeatureEntity feature = featureMapper.toFeature(dto);
        return featureMapper.toDto(featureJDBCService.updateFeature(existed, feature));
    }

    public StorageResponse updateStorageFeatureByCodes(
            Long storageId,
            List<String> featuresCodes,
            UUID userId
    ) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(storageId);

        // check the ownerId by userId
        if (!storage.getOwner().equals(userId)) {
            throw new IllegalOperationException(
                    String.format(
                            "user[%s] try to access secure information",
                            userId
                    )
            );
        }

        featureJDBCService.updateStorageFeatureByCode(featuresCodes, storageId);
        return storageMapper.toResponse(storage);
    }

    public FeatureDTO deleteFeatureByCode(String code) {

        // find the feature by code
        FeatureEntity feature = featureJDBCService.findFeatureByCode(code);

        // delete the feature by code
        featureJDBCService.deleteFeatureByCode(code);
        return featureMapper.toDto(feature);
    }

    public FeatureDTO findFeatureByCode(String code) {
        return featureMapper.toDto(featureJDBCService.findFeatureByCode(code));
    }

    public Page<FeatureDTO> findAllFeatures(Pageable pageable) {
        return featureJDBCService.findAllFeatures(pageable)
                .map(featureMapper::toDto);
    }

    public List<FeatureDTO> findAllFeaturesByStorageId(Long storageId) {
        return featureJDBCService.findFeaturesByStorageId(storageId)
                .stream().map(featureMapper::toDto).toList();
    }
}
