package com.microservices.user.customers.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.customers.requests.CustomerRequest;
import com.microservices.user.customers.requests.NewCustomerRequest;
import com.microservices.user.customers.responses.CustomerResponse;
import com.microservices.user.customers.services.CustomerServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing customer-related operations.
 * This controller handles requests related to customer registration, updates, deletions,
 * retrieval by ID, and listing all customers. It interacts with the {@link CustomerServiceManagement} service
 * to perform these operations and maps the results to appropriate response DTOs.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceManagement service;

    /**
     * @see com.microservices.user.application.mapper.MessageMapper
     */
    private final MessageMapper mapper;

    /**
     * Registers a new customer.
     *
     * @param request the {@link NewCustomerRequest} containing customer registration details.
     * @return a {@link ResponseEntity} containing the registered {@link CustomerResponse}.
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody NewCustomerRequest request) {
        CustomerResponse response = service.register(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Updates an existing customer's details.
     *
     * @param request the {@link CustomerRequest} containing updated customer information.
     * @return a {@link ResponseEntity} containing the updated {@link CustomerResponse}.
     * @since 1.0
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = service.update(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Deletes a customer by their unique ID.
     *
     * @param uid the UUID of the customer to be deleted.
     * @return a {@link ResponseEntity} containing the deleted {@link CustomerResponse}.
     * @since 1.0
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID uid) {
        CustomerResponse response = service.delete(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds a customer by their unique ID.
     *
     * @param uid the UUID of the customer to be retrieved.
     * @return a {@link ResponseEntity} containing the {@link CustomerResponse} for the specified customer.
     * @since 1.0
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") UUID uid) {
        CustomerResponse response = service.findById(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Finds all customers with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link CustomerResponse}.
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<CustomerResponse> responses = service.findAllCustomers(pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
