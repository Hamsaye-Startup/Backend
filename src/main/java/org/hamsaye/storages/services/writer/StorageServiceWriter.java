package org.hamsaye.storages.services.writer;

import org.hamsaye.generals.services.ReadService;
import org.hamsaye.generals.services.WriteService;
import org.hamsaye.storages.daos.StorageRepository;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StorageServiceWriter implements WriteService<StorageEntity> {

    private final StorageRepository storageRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageServiceWriter(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageEntity persist(StorageEntity object) {
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
        return storageRepository.save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageEntity persistAndFlush(StorageEntity object) {
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
        return storageRepository.saveAndFlush(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageEntity update(StorageEntity object) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(StorageEntity object) {
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
        if (storageRepository.exists(Example.of(object))) {
            storageRepository.delete(object);
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
