package org.hamsaye.storages.services.writer;

import org.hamsaye.generals.services.WriteService;
import org.hamsaye.storages.daos.StorageFeatureRepository;
import org.hamsaye.storages.models.StorageFeatureEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageFeatureServiceWriter implements WriteService<StorageFeatureEntity> {
    
    private final StorageFeatureRepository featureRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageFeatureServiceWriter(StorageFeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageFeatureEntity persist(StorageFeatureEntity object) {
        // check null point exception
        if (object == null) {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "null pointer exception is happened in {0})",
                            this.getClass().getName()
                    )
            );

            // TODO throw an exception
            throw new RuntimeException();
        }

        // persist object
        return featureRepository.save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageFeatureEntity persistAndFlush(StorageFeatureEntity object) {
        // check null point exception
        if (object == null) {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "null pointer exception is happened in {0})",
                            this.getClass().getName()
                    )
            );

            // TODO throw an exception
            throw new RuntimeException();
        }

        // persist object
        return featureRepository.saveAndFlush(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageFeatureEntity update(StorageFeatureEntity object) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(StorageFeatureEntity object) {
        // check null pointer exception
        if (object == null) {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "null pointer exception is happened in {0})",
                            this.getClass().getName()
                    )
            );

            // TODO throw an exception
            throw new RuntimeException();
        }

        // check the object is existed or not and if yes, it should be deleted
        if (featureRepository.exists(Example.of(object))) {
            featureRepository.delete(object);
        } else {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "{0} object with uid = {1} is not exist in our storage dataset.)",
                            object, object.getUid()
                    )
            );

            // TODO throw an exception if it doesn't exist
            throw new RuntimeException();
        }
    }
}
