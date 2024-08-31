package com.microservices.user.customers.services;

import com.microservices.user.customers.mappers.CustomerMapper;
import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.requests.CustomerNotifyRequest;
import com.microservices.user.customers.requests.CustomerNotifyType;
import com.microservices.user.customers.requests.CustomerRequest;
import com.microservices.user.customers.requests.NewCustomerRequest;
import com.microservices.user.customers.responses.CustomerResponse;
import com.microservices.user.kafka.producers.CustomerProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceManagement {

    private final CustomerMapper mapper;
    private final CustomerService service;

    private final CustomerProducerService customerProducerService;

    /*
    * register new customer
    * */
    public CustomerResponse register(NewCustomerRequest request) {
        // generate a customer
        CustomerEntity customer = mapper.toCustomerEntity(request);

        // persist the customer
        CustomerEntity persistedCustomer = service.persist(customer);

        // send a notification
        customerProducerService.send(
                CustomerNotifyRequest.builder()
                        .customerInfo(mapper.toCustomerDTO(persistedCustomer))
                        .message(CustomerNotifyType.NEW_USER.getMessage())
                        .type(CustomerNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(persistedCustomer);
    }

    /*
     * update general attributes of customer
     * */
    public CustomerResponse update(CustomerRequest request) {
        // generate a customer
        CustomerEntity customer = service.findById(request.uid());
        CustomerEntity newCustomer = mapper.toCustomerEntity(request, customer);

        // update the customer information
        CustomerEntity updatedCustomer = service.update(newCustomer, newCustomer.getLoyaltyStatus());

        // send a notification
        customerProducerService.send(
                CustomerNotifyRequest.builder()
                        .customerInfo(mapper.toCustomerDTO(updatedCustomer))
                        .message(CustomerNotifyType.UPDATE_USER_INFO.getMessage())
                        .type(CustomerNotifyType.UPDATE_USER_INFO)
                        .build()
        );

        return mapper.toResponse(updatedCustomer);
    }

    public CustomerResponse delete(UUID uid) {
        // fetch the customer by uid
        CustomerEntity customer = service.findById(uid);

        // delete the customer information
        CustomerEntity deletedCustomer = service.delete(customer);

        // send a notification
        customerProducerService.send(
                CustomerNotifyRequest.builder()
                        .customerInfo(mapper.toCustomerDTO(deletedCustomer))
                        .message(CustomerNotifyType.DELETE_USER_INFO.getMessage())
                        .type(CustomerNotifyType.DELETE_USER_INFO)
                        .build()
        );

        return mapper.toResponse(deletedCustomer);
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
    public Page<CustomerResponse> findAllCustomers(Pageable pageable) {
        return service.findAllCustomers(pageable)
                .map(mapper::toResponse);
    }
}
