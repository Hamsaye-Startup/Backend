package org.hamsaye.storages.services.writer;

import org.hamsaye.generals.services.WriteService;
import org.hamsaye.storages.daos.StorageCategoryRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageCategoryServiceWriter implements WriteService<StorageCategoryEntity> {

    private final StorageCategoryRepository categoryRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageCategoryServiceWriter(StorageCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageCategoryEntity persist(StorageCategoryEntity object) {
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
        return categoryRepository.save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageCategoryEntity persistAndFlush(StorageCategoryEntity object) {
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
        return categoryRepository.saveAndFlush(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageCategoryEntity update(StorageCategoryEntity object) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(StorageCategoryEntity object) {
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
        if (categoryRepository.exists(Example.of(object))) {
            categoryRepository.delete(object);
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
