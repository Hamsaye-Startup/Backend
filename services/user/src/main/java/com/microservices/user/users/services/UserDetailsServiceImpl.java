package com.microservices.user.users.services;

import com.microservices.user.users.exceptions.NotFoundUserException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for loading user details from the repository.
 * This service is used to retrieve user information based on either phone number or username (UUID).
 * It implements {@link org.springframework.security.core.userdetails.UserDetailsService}
 * for integrating with Spring Security.
 *
 * @see com.microservices.user.users.repositories.UserRepository
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see UserEntity
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    /**
     * Loads a user by their phone number.
     *
     * @param phone The phone number of the user.
     * @return The {@link UserEntity} corresponding to the given phone number.
     * @throws UsernameNotFoundException if the user is not found.
     * @since 1.0
     */
    public UserEntity loadUserByPhone(String phone) throws UsernameNotFoundException {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    /**
     * Loads a user by their username, which is their UUID.
     * This method is used by Spring Security during authentication.
     *
     * @param username The UUID of the user, passed as a String.
     * @return The {@link UserDetails} corresponding to the given UUID.
     * @throws UsernameNotFoundException if the user is not found.
     * @since 1.0
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(UUID.fromString(username))
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
