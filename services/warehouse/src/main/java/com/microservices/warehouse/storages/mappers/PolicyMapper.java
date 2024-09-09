package com.microservices.warehouse.storages.mappers;


import com.microservices.warehouse.storages.dto.PolicyDTO;
import com.microservices.warehouse.storages.models.PolicyEntity;
import org.springframework.stereotype.Service;

/**
 * This class is a mapper responsible for converting between Policy entity and PolicyDTO.
 * It handles the transformation of data from the database entity (PolicyEntity) to the data transfer object (PolicyDTO) and vice versa.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class PolicyMapper {

    /**
     * This method converts a PolicyDTO object into a PolicyEntity object.
     * It maps fields such as the policy code, title, and description from the DTO to the entity.
     *
     * @param dto The PolicyDTO object containing the policy's information.
     * @return A PolicyEntity object representing the policy in the database.
     * @since 1.0
     */
    public PolicyEntity toPolicy(PolicyDTO dto) {
        return PolicyEntity.builder()
                .code(dto.code())
                .title(dto.title())
                .desc(dto.description())
                .build();
    }

    /**
     * This method converts a PolicyEntity object into a PolicyDTO object.
     * It provides the policy's code, title, and description to be transferred as a response to the client.
     *
     * @param policy The PolicyEntity object representing the policy from the database.
     * @return A PolicyDTO object containing the policy information to be sent to the client.
     * @since 1.0
     */
    public PolicyDTO toDto(PolicyEntity policy) {
        return PolicyDTO.builder()
                .code(policy.getCode())
                .title(policy.getTitle())
                .description(policy.getDesc())
                .build();
    }
}
