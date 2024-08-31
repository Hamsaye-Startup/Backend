package com.microservices.user.customers.services;

import com.microservices.user.application.responses.ResponseMessageType;
import com.microservices.user.customers.exceptions.NotFoundCustomerException;
import com.microservices.user.customers.exceptions.PersistCustomerException;
import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.models.UserLoyaltyStatus;
import com.microservices.user.customers.repositories.CustomerRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            customer.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
            return repository.saveAndFlush(customer);
        } catch (RuntimeException ex) {
            throw new PersistCustomerException(ex.getCause(), customer.getNid());
        }
    }

    @Transactional(propagation = REQUIRED)
    public CustomerEntity update(CustomerEntity customer, UserLoyaltyStatus loyaltyStatus) {
        try {
            customer.setLoyaltyStatus(loyaltyStatus);
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
    public Page<CustomerEntity> findAllCustomers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = REQUIRED)
    public CustomerEntity delete(CustomerEntity customer) {
        repository.delete(customer);
        return customer;
    }
}
