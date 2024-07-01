package org.hamsaye.storages.services.reader;

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

@Service
public class StorageCategoryServiceReader implements ReadService<StorageCategoryEntity> {

    private final StorageCategoryRepository categoryRepository;

    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageCategoryServiceReader(StorageCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageCategoryEntity findByCode(String code) {
        StorageCategoryEntity category = categoryRepository.findStorageCategoryEntityByCode(code);
        if (category == null) {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "{0} is not exist in our storage's category dataset.)",
                            code
                    )
            );

            // throw an exception
            throw new RuntimeException();
        }
        return category;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<StorageCategoryEntity> findAll() {
        // find all storage's categories without any condition
        // Note: this is really dangerous , don't use this function as much as possible
        return categoryRepository.findAll();
    }
}
