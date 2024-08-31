package com.microservices.user.users.services;

import com.microservices.user.application.responses.ResponseMessageType;
import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.users.exceptions.NotFoundUserException;
import com.microservices.user.users.exceptions.PersistUserException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.repositories.UserRepository;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.hibernate.JDBCException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserEntity findByUid(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundUserException(uid.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity persist(UserEntity user, RoleEntity role, PasswordEntity password) {

        try {

            user.setRole(role);
            user.setPasswordEntity(password);
            user.setEnabled(true);
            return repository.saveAndFlush(user);

        } catch (RuntimeException ex) {
            throw new PersistUserException(ex.getCause(), user.getPhone());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity update(UserEntity user, RoleEntity role) {

        try {
            user.setRole(role);
            return repository.saveAndFlush(user);

        } catch (RuntimeException ex) {
            throw new PersistUserException(ex.getCause(), user.getPhone());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity update(UserEntity user) {

        try {
            return repository.saveAndFlush(user);
        } catch (RuntimeException ex) {
            throw new PersistUserException(ex.getCause(), user.getPhone());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<UserEntity> findAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity delete(UserEntity user) {
        repository.delete(user);
        return user;
    }
}
