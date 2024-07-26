package com.microservices.user.passwords.repositories;

import com.microservices.user.passwords.models.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {
    
}
