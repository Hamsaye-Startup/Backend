package com.microservices.warehouse.storages.mappers;

import com.microservices.warehouse.storages.dto.FeatureDTO;
import com.microservices.warehouse.storages.models.FeatureEntity;
import org.springframework.stereotype.Service;

/**
 * This class is a mapper class for converting between Feature entity and DTO (Data Transfer Object).
 * It helps in converting data from the FeatureEntity (database entity) to FeatureDTO (outgoing response) and vice versa.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class FeatureMapper {

    /**
     * This method converts a FeatureDTO object into a FeatureEntity object.
     * It maps the code, title, and description from the DTO to the entity.
     *
     * @param dto The FeatureDTO object containing the feature's information.
     * @return A FeatureEntity object representing the feature in the database.
     * @since 1.0
     */
    public FeatureEntity toFeature(FeatureDTO dto) {
        return FeatureEntity.builder()
                .code(dto.code())
                .title(dto.title())
                .desc(dto.description())
                .build();
    }

    /**
     * This method converts a FeatureEntity object into a FeatureDTO object.
     * It provides the feature's code, title, and description to the client.
     *
     * @param feature The FeatureEntity object representing the feature from the database.
     * @return A FeatureDTO object containing the information of the feature to be sent to the client.
     * @since 1.0
     */
    public FeatureDTO toDto(FeatureEntity feature) {
        return FeatureDTO.builder()
                .code(feature.getCode())
                .title(feature.getTitle())
                .description(feature.getDesc())
                .build();
    }
}
