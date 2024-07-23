package com.hamsaye.customer.customers.mappers;

import com.hamsaye.customer.customers.requests.CustomerRequest;
import com.hamsaye.customer.customers.requests.NewCustomerRequest;
import com.hamsaye.customer.customers.responses.CustomerResponse;
import com.hamsaye.customer.customers.models.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final CustomerStatusMapper statusMapper;

    public CustomerEntity toCustomerEntity(NewCustomerRequest request) {
        return CustomerEntity.builder()
                .nid(request.nid())
                .bio(request.bio())
                .userId(request.userId())
                .build();
    }

    public CustomerEntity toCustomerEntity(CustomerRequest request, CustomerEntity customer) {
        return CustomerEntity.builder()
                .uid(request.uid())
                .nid(request.nid() == null ? customer.getNid() : request.nid())
                .bio(request.bio() == null ? customer.getBio() : request.bio())
                .userId(request.userId() == null ? customer.getUserId() : request.userId())
                .build();
    }

    public CustomerResponse toResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .uid(customer.getUid())
                .userId(customer.getUserId())
                .createdAt(customer.getCreatedAt())
                .nid(customer.getNid())
                .bio(customer.getBio())
                .status(statusMapper.toList(customer.getStatus()))
                .build();
    }
}
