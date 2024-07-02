package org.hamsaye.storages.services.reader;

import org.hamsaye.generals.services.ReadService;
import org.hamsaye.generals.services.WriteService;
import org.hamsaye.storages.daos.StorageRepository;
import org.hamsaye.storages.exceptions.NotFoundStorageException;
import org.hamsaye.storages.exceptions.StorageException;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StorageServiceReader implements ReadService<StorageEntity> {

    private final StorageRepository storageRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageServiceReader(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageEntity findById(UUID uuid) {
        StorageEntity storage = storageRepository.findByUid(uuid);
        if (storage == null) {

            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "{0} is not exist in our storage dataset.",
                            uuid
                    )
            );

            // throw an exception
            throw new NotFoundStorageException();
        }
        return storage;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageEntity findByName(String name) {
        // find a specific storage by its id and if it is not existed throw specific exception
        return storageRepository.findByName(name)
                .orElseThrow(() -> {
                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "storage exception occurred in repo layer by {0}",
                                    name
                            )
                    );

                    // throw an exception
                    return new StorageException("storage exception occurred in repo layer");
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<StorageEntity> findAll() {
        // find all storages without any condition
        // Note: this is really dangerous , don't use this function as much as possible
        return storageRepository.findAll();
    }
}
