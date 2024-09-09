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

/**
 * Service class for managing customer operations including registration, updates, deletions, and retrieval.
 * This class interacts with the {@link CustomerService} for persistence operations and the {@link CustomerProducerService}
 * for sending notifications about customer-related changes.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceManagement {

    private final CustomerMapper mapper;
    private final CustomerService service;
    private final CustomerProducerService customerProducerService;

    /**
     * Registers a new customer and sends a notification about the new registration.
     *
     * @param request the {@link NewCustomerRequest} containing details of the new customer.
     * @return the {@link CustomerResponse} for the newly registered customer.
     * @since 1.0
     */
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

    /**
     * Updates an existing customer and sends a notification about the updated information.
     *
     * @param request the {@link CustomerRequest} containing updated customer details.
     * @return the {@link CustomerResponse} for the updated customer.
     * @since 1.0
     */
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

    /**
     * Deletes a customer and sends a notification about the deletion.
     *
     * @param uid the UUID of the customer to be deleted.
     * @return the {@link CustomerResponse} for the deleted customer.
     * @since 1.0
     */
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

    /**
     * Finds a customer by its unique ID.
     *
     * @param uid the UUID of the customer to be retrieved.
     * @return the {@link CustomerResponse} for the customer with the specified ID.
     * @since 1.0
     */
    public CustomerResponse findById(UUID uid) {
        return mapper.toResponse(service.findById(uid));
    }

    /**
     * Finds all customers with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link CustomerResponse} objects.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<CustomerResponse> findAllCustomers(Pageable pageable) {
        return service.findAllCustomers(pageable)
                .map(mapper::toResponse);
    }
}
