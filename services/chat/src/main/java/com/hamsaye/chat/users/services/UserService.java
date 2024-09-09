package com.hamsaye.chat.users.services;

import com.hamsaye.chat.applications.config.VersionControlConfig;
import com.hamsaye.chat.users.exceptions.NotFoundUserException;
import com.hamsaye.chat.users.exceptions.PersistUserException;
import com.hamsaye.chat.users.models.*;
import com.hamsaye.chat.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for managing user-related operations including saving, updating, deleting, and retrieving user entities.
 * <p>
 * This service interacts with the {@link UserRepository} for data persistence and retrieval, and uses
 * {@link VersionControlConfig} to set version control information on user entities.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repository for user data persistence and retrieval.
     *
     * @see com.hamsaye.chat.users.repositories.UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Configuration for version control information.
     *
     * @see com.hamsaye.chat.applications.config.VersionControlConfig
     */
    private final VersionControlConfig versionControlConfig;

    /**
     * Saves a user entity along with its connection state.
     * <p>
     * Sets the version control information and loyalty status before saving the user entity.
     * </p>
     *
     * @param user the user entity to save
     * @param connectionState the connection state to associate with the user
     * @return the saved user entity
     * @since 1.0
     */
    public UserEntity saveUser(UserEntity user, UserConnectionState connectionState) {
        user.setVersionControl(VersionControl.builder()
                .appVersion(versionControlConfig.appVersion())
                .build());

        user.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
        user.setConnectionState(connectionState);
        return userRepository.save(user);
    }

    /**
     * Saves a user entity.
     * <p>
     * Sets the version control information and loyalty status before saving the user entity. If an error occurs during
     * saving, a {@link PersistUserException} is thrown.
     * </p>
     *
     * @param user the user entity to save
     * @return the saved user entity
     * @throws PersistUserException if an error occurs while saving the user
     * @since 1.0
     */
    public UserEntity saveUser(UserEntity user) {
        try {
            user.setVersionControl(VersionControl.builder()
                    .appVersion(versionControlConfig.appVersion())
                    .build());

            user.setLoyaltyStatus(UserLoyaltyStatus.NEW_USER);
            return userRepository.save(user);
        }
        catch (RuntimeException exception) {
            throw new PersistUserException(exception.getCause(), user.getUid().toString());
        }
    }

    /**
     * Updates an existing user entity.
     * <p>
     * Sets the version control information before updating the user entity. The existing user entity is deleted by ID
     * and then the updated user entity is saved. If an error occurs during the process, a {@link PersistUserException}
     * is thrown.
     * </p>
     *
     * @param user the user entity to update
     * @throws PersistUserException if an error occurs while updating the user
     * @since 1.0
     */
    public void updateUser(UserEntity user) {
        try {
            user.setVersionControl(VersionControl.builder()
                    .appVersion(versionControlConfig.appVersion())
                    .build());

            userRepository.deleteById(user.getUid());
            userRepository.save(user);
        }
        catch (RuntimeException exception) {
            throw new PersistUserException(exception.getCause(), user.getUid().toString());
        }
    }

    /**
     * Deletes a user entity.
     *
     * @param user the user entity to delete
     * @since 1.0
     */
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    /**
     * Connects a user by updating their connection state.
     * <p>
     * If an error occurs during the process, a {@link PersistUserException} is thrown.
     * </p>
     *
     * @param user the user entity to update
     * @param connectionState the connection state to set for the user
     * @return the updated user entity
     * @throws PersistUserException if an error occurs while updating the user's connection state
     * @since 1.0
     */
    public UserEntity connectUser(UserEntity user, UserConnectionState connectionState) {
        try {
            user.setConnectionState(connectionState);
            return userRepository.save(user);
        }
        catch (RuntimeException exception) {
            throw new PersistUserException(exception.getCause(), user.getUid().toString());
        }
    }

    /**
     * Finds a user entity by its ID.
     * <p>
     * If the user with the specified ID is not found, a {@link NotFoundUserException} is thrown.
     * </p>
     *
     * @param uid the UUID of the user to find
     * @return the found user entity
     * @throws NotFoundUserException if no user with the specified ID is found
     * @since 1.0
     */
    public UserEntity findUserById(UUID uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new NotFoundUserException(uid.toString()));
    }
}
