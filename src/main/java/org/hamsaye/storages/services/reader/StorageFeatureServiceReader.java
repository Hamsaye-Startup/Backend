package org.hamsaye.storages.services.reader;

import org.hamsaye.generals.services.ReadService;
import org.hamsaye.storages.daos.StorageFeatureRepository;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StorageFeatureServiceReader implements ReadService<StorageFeatureEntity> {

    private final StorageFeatureRepository featureRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageFeatureServiceReader(StorageFeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageFeatureEntity findById(UUID uid) {
        // find a specific storage's feature by its id and if it is not existed throw specific exception
        return featureRepository.findById(uid)
                .orElseThrow(() -> {
                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "{0} is not exist in our storage's feature dataset.)",
                                    uid
                            )
                    );

                    // throw an exception
                    return new RuntimeException();
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageFeatureEntity findByTitle(String title) {
        // find a specific storage's feature by its id and if it is not existed throw specific exception
        return featureRepository.selectByTitle(title)
                .orElseThrow(() -> {
                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "{0} is not exist in our storage's feature dataset.)",
                                    title
                            )
                    );

                    // throw an exception
                    return new RuntimeException();
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<StorageFeatureEntity> findAll() {
        // find all storage's features without any condition
        // Note: this is really dangerous , don't use this function as much as possible
        return featureRepository.findAll();
    }
}
