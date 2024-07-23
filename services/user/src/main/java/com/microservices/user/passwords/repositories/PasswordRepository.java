package com.microservices.user.passwords.repositories;

import com.microservices.user.passwords.model.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
    
}
