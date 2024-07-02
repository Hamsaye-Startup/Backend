package org.hamsaye.geo.services.writer;

import lombok.RequiredArgsConstructor;
import org.hamsaye.generals.services.WriteService;
import org.hamsaye.geo.daos.StorageLocationRepository;
import org.hamsaye.geo.exceptions.NotFoundStorageLocationException;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StorageLocationServiceWriter implements WriteService<StorageLocationEntity> {

    private StorageLocationRepository locationRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageLocationServiceWriter(StorageLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageLocationEntity persist(StorageLocationEntity object) {
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

            // throw an exception
            throw new NullPointerException();
        }

        // persist object
        return locationRepository.save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageLocationEntity persistAndFlush(StorageLocationEntity object) {
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

            // throw an exception
            throw new NullPointerException();
        }

        // persist object
        return locationRepository.saveAndFlush(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public StorageLocationEntity update(StorageLocationEntity object) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(StorageLocationEntity object) {
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

            // throw an exception
            throw new NullPointerException();
        }

        // check the object is existed or not and if yes, it should be deleted
        if (locationRepository.exists(Example.of(object))) {
            locationRepository.delete(object);
        } else {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "{0} object with uid = {1} is not exist in our geo dataset.)",
                            object, object.getId()
                    )
            );

            // throw an exception if it doesn't exist
            throw new NotFoundStorageLocationException();
        }
    }
}
