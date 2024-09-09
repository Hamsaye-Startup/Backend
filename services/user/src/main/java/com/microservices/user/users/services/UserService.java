package com.microservices.user.users.services;

import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.users.exceptions.NotFoundUserException;
import com.microservices.user.users.exceptions.PersistUserException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class responsible for handling user-related operations.
 * This service interacts with the {@link UserRepository} to perform CRUD operations
 * for the {@link UserEntity} class.
 *
 * @see com.microservices.user.users.repositories.UserRepository
 * @see UserEntity
 * @see RoleEntity
 * @see PasswordEntity
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param uid The UUID of the user.
     * @return The {@link UserEntity} associated with the given UUID.
     * @throws NotFoundUserException if the user is not found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserEntity findByUid(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundUserException(uid.toString()));
    }

    /**
     * Saves a new user with the provided role and password.
     *
     * @param user The user entity to be persisted.
     * @param role The role associated with the user.
     * @param password The password entity for the user.
     * @return The saved {@link UserEntity}.
     * @throws PersistUserException if there is an error while saving the user.
     * @since 1.0
     */
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

    /**
     * Updates an existing user with a new role.
     *
     * @param user The user entity to be updated.
     * @param role The new role to be associated with the user.
     * @return The updated {@link UserEntity}.
     * @throws PersistUserException if there is an error while updating the user.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity update(UserEntity user, RoleEntity role) {
        try {
            user.setRole(role);
            return repository.saveAndFlush(user);
        } catch (RuntimeException ex) {
            throw new PersistUserException(ex.getCause(), user.getPhone());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user The user entity to be updated.
     * @return The updated {@link UserEntity}.
     * @throws PersistUserException if there is an error while updating the user.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity update(UserEntity user) {
        try {
            return repository.saveAndFlush(user);
        } catch (RuntimeException ex) {
            throw new PersistUserException(ex.getCause(), user.getPhone());
        }
    }

    /**
     * Retrieves a paginated list of all users.
     *
     * @param pageable Pagination details.
     * @return A {@link Page} of {@link UserEntity} instances.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<UserEntity> findAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Deletes the specified user entity.
     *
     * @param user The user entity to delete.
     * @return The deleted {@link UserEntity}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity delete(UserEntity user) {
        repository.delete(user);
        return user;
    }
}
