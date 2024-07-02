package org.hamsaye.geo.services.reader;

import lombok.RequiredArgsConstructor;
import org.hamsaye.generals.services.ReadService;
import org.hamsaye.geo.daos.CityRepository;
import org.hamsaye.geo.exceptions.CityException;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceReader implements ReadService<CityEntity> {

    private CityRepository cityRepository;
    private final Logger logger = Logger.getInstance();

    @Autowired
    public CityServiceReader(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public CityEntity findById(UUID uid) {
        // find a specific city by its id and if it is not existed throw specific exception
        return cityRepository.findById(uid)
                .orElseThrow(() -> {

                    // generate an error log
                    logger.log(
                            LogLevel.ERROR,
                            String.format(
                                    "city exception occurred in repo layer by {0}",
                                    uid
                            )
                    );

                    // throw an exception
                    return new CityException("city exception occurred in repo layer");
                });
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<CityEntity> findAll() {
        // find all cities without any condition
        // Note: this is really dangerous , don't use this function as much as possible
        return cityRepository.findAll();
    }
}
