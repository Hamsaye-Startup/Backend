package com.microservices.user.customers.mappers;

import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.requests.CustomerRequest;
import com.microservices.user.customers.requests.NewCustomerRequest;
import com.microservices.user.customers.responses.CustomerResponse;
import com.microservices.user.users.mappers.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final UserService userService;

    private final CustomerStatusMapper statusMapper;
    private final UserMapper userMapper;

    public CustomerEntity toCustomerEntity(NewCustomerRequest request) {
        // find the user by id
        UserEntity user = userService.findByUid(request.userId());
        return CustomerEntity.builder()
                .nid(request.nid())
                .bio(request.bio())
                .user(user)
                .build();
    }

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
                .build();
    }

    public CustomerResponse toResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .uid(customer.getUid())
                .user(userMapper.toResponse(customer.getUser()))
                .createdAt(customer.getCreatedAt())
                .nid(customer.getNid())
                .bio(customer.getBio())
                .status(statusMapper.toList(customer.getStatus()))
                .build();
    }
}
