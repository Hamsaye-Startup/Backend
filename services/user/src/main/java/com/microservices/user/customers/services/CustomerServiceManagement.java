package com.microservices.user.customers.services;

import com.microservices.user.customers.mappers.CustomerMapper;
import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.requests.CustomerRequest;
import com.microservices.user.customers.requests.NewCustomerRequest;
import com.microservices.user.customers.responses.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceManagement {

    private final CustomerMapper mapper;
    private final CustomerService service;

    /*
    * register new customer
    * */
    public CustomerResponse register(NewCustomerRequest request) {
        // generate a customer
        CustomerEntity customer = mapper.toCustomerEntity(request);

        // todo set default the status
        customer.setStatus("");

        return mapper.toResponse(service.persist(customer));
    }

    /*
     * update general attributes of customer
     * */
    public CustomerResponse update(CustomerRequest request) {
        // generate a customer
        CustomerEntity customer = service.findById(request.uid());
        CustomerEntity newCustomer = mapper.toCustomerEntity(request, customer);
        return mapper.toResponse(service.persist(newCustomer));
    }

    public CustomerResponse delete(UUID uid) {
        // fetch the customer by uid
        CustomerEntity customer = service.findById(uid);
        return mapper.toResponse(service.delete(customer));
    }

    /*
     * find the customer by uid
     * */
    public CustomerResponse findById(UUID uid) {
        return mapper.toResponse(service.findById(uid));
    }

    /*
     * find the 20 of last customer based on the timestamp offset
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CustomerResponse> findAllCustomers(LocalDateTime offset) {

        // fetch the customers
        List<CustomerEntity> customers;
        if (offset == null) {
            customers = service.findAllCustomers();
        } else {
            customers = service.findAllCustomers(offset);
        }

        return customers.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
