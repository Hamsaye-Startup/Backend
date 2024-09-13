package com.microservices.user.users.services;

import com.microservices.user.users.exceptions.NotFoundUserDetailException;
import com.microservices.user.users.exceptions.PersistUserDetailException;
import com.microservices.user.users.models.UserConnectionStatus;
import com.microservices.user.users.models.UserDetailEntity;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.models.UserLoyaltyStatus;
import com.microservices.user.users.repositories.UserDetailRepository;
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
 * It interacts with the {@link UserDetailRepository} to perform these operations and handle exceptions
 * related to customer persistence and retrieval.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository repository;

    /**
     * Persists a new user detail entity with an initial loyalty status of {@link UserLoyaltyStatus}.
     *
     * @param user the {@link UserDetailEntity} to be persisted.
     * @return the persisted {@link UserDetailEntity}.
     * @throws PersistUserDetailException if there is an error during persistence.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public UserDetailEntity insert(UserDetailEntity user) {
        try {
            user.setLoyalty(UserLoyaltyStatus.NEW_USER);
            user.setConnectionStatus(UserConnectionStatus.ONLINE);
            return repository.saveAndFlush(user);
        } catch (RuntimeException ex) {
            throw new PersistUserDetailException(ex.getCause(), user.getNid());
        }
    }

    /**
     * Updates an existing user detail entity.
     *
     * @return the updated {@link UserDetailEntity}.
     * @throws PersistUserDetailException if there is an error during persistence.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public UserDetailEntity update(UserDetailEntity user, UserDetailEntity exists) {
        try {

            exists.setBio(user.getBio());
            exists.setUser(user.getUser());
            return repository.saveAndFlush(exists);

        } catch (RuntimeException ex) {
            throw new PersistUserDetailException(ex.getCause(), user.getUser().getUid().toString());
        }
    }

    /**
     * Finds a user detail entity by its unique ID.
     *
     * @return the {@link UserDetailEntity} with the specified ID.
     * @throws NotFoundUserDetailException if no user is found with the given ID.
     * @since 1.0
     */
    @Transactional(readOnly = true, propagation = REQUIRED)
    public UserDetailEntity findByUserId(UserEntity user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new NotFoundUserDetailException(user.getUid().toString()));
    }

    /**
     * Deletes a user detail entity.
     *
     * @param user the {@link UserDetailEntity} to be deleted.
     * @return the deleted {@link UserDetailEntity}.
     * @since 1.0
     */
    @Transactional(propagation = REQUIRED)
    public UserDetailEntity delete(UserDetailEntity user) {
        repository.delete(user);
        return user;
    }

    @Transactional(readOnly = true, propagation = REQUIRED)
    public Page<UserDetailEntity> findAllByRoleIdAndConnectionStatus(
            UUID roleId,
            UserConnectionStatus connection,
            Pageable pageable
    ) {
        return repository.findAllUsersByRoleIdAndConnectionStatus(
                roleId,
                connection,
                pageable
        );
    }
}
