package com.microservices.user.users.services;

import com.microservices.user.users.exceptions.NotFoundUserException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;

    public UserEntity loadUserByPhone(String phone) throws UsernameNotFoundException {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(UUID.fromString(username))
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
