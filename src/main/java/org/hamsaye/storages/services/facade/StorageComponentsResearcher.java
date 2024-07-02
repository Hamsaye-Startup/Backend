package org.hamsaye.storages.services.facade;

import lombok.RequiredArgsConstructor;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.storages.services.reader.StorageCategoryServiceReader;
import org.hamsaye.storages.services.reader.StorageFeatureServiceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageComponentsResearcher {

    private StorageCategoryServiceReader categoryServiceReader;
    private StorageFeatureServiceReader featureServiceReader;

    @Autowired
    public StorageComponentsResearcher(StorageCategoryServiceReader categoryServiceReader,
                                       StorageFeatureServiceReader featureServiceReader) {
        this.categoryServiceReader = categoryServiceReader;
        this.featureServiceReader = featureServiceReader;
    }

    public StorageFeatureEntity findStorageFeature(UUID uid) {
        return featureServiceReader.findById(uid);
    }

    public StorageCategoryEntity findStorageCategory(String code) {
        return categoryServiceReader.findByCode(code);
    }
}
