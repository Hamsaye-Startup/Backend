package org.hamsaye.geo.services.writer;

import lombok.RequiredArgsConstructor;
import org.hamsaye.generals.services.WriteService;
import org.hamsaye.geo.daos.CityRepository;
import org.hamsaye.geo.exceptions.NotFoundCityException;
import org.hamsaye.geo.models.CityEntity;
import org.hamsaye.utils.log.LogLevel;
import org.hamsaye.utils.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CityServiceWriter implements WriteService<CityEntity> {

    private CityRepository cityRepository;
    private final Logger logger = Logger.getInstance();

    @Autowired
    public CityServiceWriter(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CityEntity persist(CityEntity object) {
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
        return cityRepository.save(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CityEntity persistAndFlush(CityEntity object) {
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
        return cityRepository.saveAndFlush(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CityEntity update(CityEntity object) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(CityEntity object) {
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
        if (cityRepository.exists(Example.of(object))) {
            cityRepository.delete(object);
        } else {
            // generate an error log
            logger.log(
                    LogLevel.ERROR,
                    String.format(
                            "{0} object with uid = {1} is not exist in our geo dataset.)",
                            object, object.getUid()
                    )
            );

            // throw an exception if it doesn't exist
            throw new NotFoundCityException();
        }
    }
}
