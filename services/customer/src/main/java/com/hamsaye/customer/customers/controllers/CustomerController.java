package com.hamsaye.customer.customers.controllers;

import com.hamsaye.customer.application.mapper.MessageMapper;
import com.hamsaye.customer.customers.requests.CustomerRequest;
import com.hamsaye.customer.customers.requests.NewCustomerRequest;
import com.hamsaye.customer.customers.responses.CustomerResponse;
import com.hamsaye.customer.customers.services.CustomerServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceManagement service;
    private final MessageMapper mapper;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody NewCustomerRequest request) {
        CustomerResponse response = service.register(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping
    public ResponseEntity<?> update( @Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = service.update(request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID uid) {
        CustomerResponse response = service.delete(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") UUID uid) {
        CustomerResponse response = service.findById(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "offset", required = false) LocalDateTime offset) {
        List<CustomerResponse> responses = service.findAllCustomers(offset);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
