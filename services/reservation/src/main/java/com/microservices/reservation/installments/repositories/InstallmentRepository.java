package com.microservices.reservation.installments.repositories;

import com.microservices.reservation.installments.models.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstallmentRepository extends JpaRepository<InstallmentEntity, UUID> {
}
