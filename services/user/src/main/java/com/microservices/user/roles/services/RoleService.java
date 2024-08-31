package com.microservices.user.roles.services;

import com.microservices.user.application.responses.ResponseMessageType;
import com.microservices.user.roles.exceptions.NotFoundRoleException;
import com.microservices.user.roles.exceptions.PersistRoleException;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.repositories.RoleRepository;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public RoleEntity persist(RoleEntity role) {
        try {
            return repository.saveAndFlush(role);
        } catch (RuntimeException ex) {
            throw new PersistRoleException(ex.getCause(), role.getName());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RoleEntity delete(RoleEntity role) {
        repository.delete(role);
        return role;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RoleEntity findRoleByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundRoleException(name));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RoleEntity findRoleById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundRoleException(uid.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<RoleEntity> findAllRoles(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
