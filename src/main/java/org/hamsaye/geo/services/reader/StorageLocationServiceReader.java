package org.hamsaye.geo.services.reader;

import lombok.RequiredArgsConstructor;
import org.hamsaye.generals.services.ReadService;
import org.hamsaye.geo.daos.StorageLocationRepository;
import org.hamsaye.geo.exceptions.StorageLocationException;
import org.hamsaye.geo.models.StorageLocationEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageLocationServiceReader implements ReadService<StorageLocationEntity> {
    private StorageLocationRepository locationRepository;
    private final Logger logger = Logger.getInstance();

    @Autowired
    public StorageLocationServiceReader(StorageLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public StorageLocationEntity findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> {

                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "storage location exception occurred in repo layer by {0}",
                                    id
                            )
                    );

                    // throw an exception
                    return new StorageLocationException("storage location exception occurred in repo layer");
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<StorageLocationEntity> findAll() {
        return locationRepository.findAll();
    }
}
