package com.microservices.reservation.transactions.repositories;

import com.microservices.reservation.transactions.models.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
