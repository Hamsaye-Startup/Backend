package com.hamsaye.customer.customers.repositories;

import com.hamsaye.customer.customers.models.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Query(value = "select c from CustomerEntity c where c.createdAt > :offsetTime order by c.createdAt desc limit 20")
    List<CustomerEntity> findAllCustomers(@Param("offsetTime") LocalDateTime lastTime);

    @Query(value = "select c from CustomerEntity c order by c.createdAt desc limit 20")
    List<CustomerEntity> findAllCustomers();
}
