package com.microservices.user.customers.mappers;

import com.microservices.user.customers.dto.CustomerDTO;
import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.models.GenderEnum;
import com.microservices.user.customers.requests.CustomerRequest;
import com.microservices.user.customers.requests.NewCustomerRequest;
import com.microservices.user.customers.responses.CustomerResponse;
import com.microservices.user.users.mappers.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between {@link CustomerEntity}, {@link CustomerDTO}, {@link CustomerRequest},
 * and {@link CustomerResponse} objects. This class handles the mapping of data between the customer model,
 * data transfer objects, and request/response objects used in the application.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Converts a {@link NewCustomerRequest} to a {@link CustomerEntity}.
     *
     * @param request the request object containing customer details.
     * @return the corresponding {@link CustomerEntity}.
     * @since 1.0
     */
    public CustomerEntity toCustomerEntity(NewCustomerRequest request) {
        // find the user by id
        UserEntity user = userService.findByUid(request.userId());
        return CustomerEntity.builder()
                .nid(request.nid())
                .bio(request.bio())
                .user(user)
                .genderEnum(GenderEnum.valueOf(request.gender()))
                .build();
    }

    /**
     * Converts a {@link CustomerRequest} to a {@link CustomerEntity}, updating an existing customer entity.
     *
     * @param request  the request object containing updated customer details.
     * @param customer the existing {@link CustomerEntity} to be updated.
     * @return the updated {@link CustomerEntity}.
     * @since 1.0
     */
    public CustomerEntity toCustomerEntity(CustomerRequest request, CustomerEntity customer) {
        // find the user by id
        UserEntity user;
        if (request.userId() == null) {
            user = customer.getUser();
        } else {
            user = userService.findByUid(request.uid());
        }
        return CustomerEntity.builder()
                .uid(request.uid())
                .nid(request.nid() == null ? customer.getNid() : request.nid())
                .bio(request.bio() == null ? customer.getBio() : request.bio())
                .user(user)
                .genderEnum(customer.getGenderEnum())
                .loyaltyStatus(customer.getLoyaltyStatus())
                .build();
    }

    /**
     * Converts a {@link CustomerEntity} to a {@link CustomerResponse}.
     *
     * @param customer the {@link CustomerEntity} to be converted.
     * @return the corresponding {@link CustomerResponse}.
     * @since 1.0
     */
    public CustomerResponse toResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .uid(customer.getUid())
                .user(userMapper.toResponse(customer.getUser()))
                .createdAt(customer.getCreatedAt())
                .nid(customer.getNid())
                .bio(customer.getBio())
                .build();
    }

    /**
     * Converts a {@link CustomerEntity} to a {@link CustomerDTO}.
     *
     * @param customer the {@link CustomerEntity} to be converted.
     * @return the corresponding {@link CustomerDTO}.
     * @since 1.0
     */
    public CustomerDTO toCustomerDTO(CustomerEntity customer) {
        return CustomerDTO.builder()
                .uid(customer.getUser().getUid())
                .genderEnum(customer.getGenderEnum())
                .loyaltyStatus(customer.getLoyaltyStatus())
                .build();
    }
}
