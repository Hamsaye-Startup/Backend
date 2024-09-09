package com.microservices.user.customers.services;

import com.microservices.user.customers.exceptions.NotFoundCustomerException;
import com.microservices.user.customers.exceptions.PersistCustomerException;
import com.microservices.user.customers.models.CustomerEntity;
import com.microservices.user.customers.models.UserLoyaltyStatus;
import com.microservices.user.customers.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * Service class for managing customer-related operations.
 * This class provides methods for persisting, updating, finding, and deleting customer entities.
 * It interacts with the {@link CustomerRepository} to perform these operations and handle exceptions
 * related to customer persistence and retrieval.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    /**
     * Persists a new customer entity with an initial loyalty status of {@link UserLoyaltyStatus#NEW_USER}.
     *
     * @param customer the {@link CustomerEntity} to be persisted.
     * @return the persisted {@link CustomerEntity}.
     * @throws PersistCustomerException if there is an error during persistence.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public CustomerEntity persist(CustomerEntity customer) {
        try {
            customer.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
            return repository.saveAndFlush(customer);
        } catch (RuntimeException ex) {
            throw new PersistCustomerException(ex.getCause(), customer.getNid());
        }
    }

    /**
     * Updates an existing customer entity with the specified loyalty status.
     *
     * @param customer the {@link CustomerEntity} to be updated.
     * @param loyaltyStatus the {@link UserLoyaltyStatus} to be set.
     * @return the updated {@link CustomerEntity}.
     * @throws PersistCustomerException if there is an error during persistence.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public CustomerEntity update(CustomerEntity customer, UserLoyaltyStatus loyaltyStatus) {
        try {
            customer.setLoyaltyStatus(loyaltyStatus);
            return repository.saveAndFlush(customer);
        } catch (RuntimeException ex) {
            throw new PersistCustomerException(ex.getCause(), customer.getNid());
        }
    }

    /**
     * Finds a customer entity by its unique ID.
     *
     * @param uid the UUID of the customer to be retrieved.
     * @return the {@link CustomerEntity} with the specified ID.
     * @throws NotFoundCustomerException if no customer is found with the given ID.
     * @since 1.0
     */
    @Transactional(readOnly = true, propagation = REQUIRED)
    public CustomerEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundCustomerException(uid.toString()));
    }

    /**
     * Finds all customer entities with pagination support.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link CustomerEntity} objects.
     * @since 1.0
     */
    @Transactional(readOnly = true, propagation = REQUIRED)
    public Page<CustomerEntity> findAllCustomers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Deletes a customer entity.
     *
     * @param customer the {@link CustomerEntity} to be deleted.
     * @return the deleted {@link CustomerEntity}.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public CustomerEntity delete(CustomerEntity customer) {
        repository.delete(customer);
        return customer;
    }
}
