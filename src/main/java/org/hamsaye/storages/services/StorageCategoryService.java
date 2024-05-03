package org.hamsaye.storages.services;

import org.hamsaye.generals.services.ReadService;
import org.hamsaye.storages.daos.StorageCategoryRepository;
import org.hamsaye.storages.models.StorageCategoryEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StorageCategoryService implements ReadService<StorageCategoryEntity> {

    private final StorageCategoryRepository categoryRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageCategoryService(StorageCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageCategoryEntity findByName(String name) {
        // find a specific storage's category by its id and if it is not existed throw specific exception
        return categoryRepository.selectByName(name)
                .orElseThrow(() -> {
                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "{0} is not exist in our storage's category dataset.)",
                                    name
                            )
                    );

                    // throw an exception
                    return new RuntimeException();
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<StorageCategoryEntity> findAll() {
        // find all storage's categories without any condition
        // Note: this is really dangerous , don't use this function as much as possible
        return categoryRepository.findAll();
    }
}
