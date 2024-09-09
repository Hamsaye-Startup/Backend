package com.microservices.user.roles.services;

import com.microservices.user.roles.exceptions.NotFoundRoleException;
import com.microservices.user.roles.exceptions.PersistRoleException;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service class responsible for managing roles in the system.
 * This class provides methods to persist, delete, and retrieve roles.
 * It handles interactions with the {@link RoleRepository} and manages role-related transactions.
 *
 * @see RoleRepository
 * @see RoleEntity
 * @see NotFoundRoleException
 * @see PersistRoleException
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    /**
     * Persists a given role entity to the repository.
     *
     * @param role The role entity to be persisted.
     * @return The persisted {@link RoleEntity}.
     * @throws PersistRoleException If an error occurs while persisting the role.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public RoleEntity persist(RoleEntity role) {
        try {
            return repository.saveAndFlush(role);
        } catch (RuntimeException ex) {
            throw new PersistRoleException(ex.getCause(), role.getName());
        }
    }

    /**
     * Deletes a given role entity from the repository.
     *
     * @param role The role entity to be deleted.
     * @return The deleted {@link RoleEntity}.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public RoleEntity delete(RoleEntity role) {
        repository.delete(role);
        return role;
    }

    /**
     * Retrieves a role entity by its name.
     *
     * @param name The name of the role to be retrieved.
     * @return The {@link RoleEntity} with the specified name.
     * @throws NotFoundRoleException If no role with the specified name is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RoleEntity findRoleByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundRoleException(name));
    }

    /**
     * Retrieves a role entity by its ID.
     *
     * @param uid The ID of the role to be retrieved.
     * @return The {@link RoleEntity} with the specified ID.
     * @throws NotFoundRoleException If no role with the specified ID is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RoleEntity findRoleById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundRoleException(uid.toString()));
    }

    /**
     * Retrieves all role entities with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RoleEntity} containing role entities.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<RoleEntity> findAllRoles(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
