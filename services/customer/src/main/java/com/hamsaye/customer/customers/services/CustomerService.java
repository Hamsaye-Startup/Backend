package com.hamsaye.customer.customers.services;

import com.hamsaye.customer.application.responses.ResponseMessageType;
import com.hamsaye.customer.customers.exceptions.NotFoundCustomerException;
import com.hamsaye.customer.customers.exceptions.PersistCustomerException;
import com.hamsaye.customer.customers.models.CustomerEntity;
import com.hamsaye.customer.customers.repositories.CustomerRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional(propagation = REQUIRED)
    public CustomerEntity persist(CustomerEntity customer) {
        try {
            return repository.saveAndFlush(customer);
        } catch (RuntimeException ex) {
            throw new PersistCustomerException(ex.getCause(), customer.getNid());
        }
    }

    @Transactional(readOnly = true, propagation = REQUIRED)
    public CustomerEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundCustomerException(uid.toString()));
    }

    @Transactional(readOnly = true, propagation = REQUIRED)
    public List<CustomerEntity> findAllCustomers(LocalDateTime timestamp) {
        try {
            return repository.findAllCustomers(timestamp);
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }

    @Transactional(readOnly = true, propagation = REQUIRED)
    public List<CustomerEntity> findAllCustomers() {
        try {
            return repository.findAllCustomers();
        } catch (RuntimeException ex) {
            throw new InternalServerErrorException(ResponseMessageType.INTERNAL.message(), ex.getCause());
        }
    }

    @Transactional(propagation = REQUIRED)
    public CustomerEntity delete(CustomerEntity customer) {
        repository.delete(customer);
        return customer;
    }
}
