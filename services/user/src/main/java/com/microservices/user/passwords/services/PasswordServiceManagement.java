package com.microservices.user.passwords.services;

import com.microservices.user.passwords.model.PasswordEntity;
import com.microservices.user.passwords.requests.PasswordRequest;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PasswordServiceManagement {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(PasswordRequest request, Principal principal) {

        // check the authenticated user with request
        if (!request.uid().toString().equals(principal.getName())) {
            throw new IllegalRequestException(request.uid().toString(), principal.getName());
        }

        // fetch the user
        UserEntity user = userService.findByUid(request.uid());

        // fetch the password
        PasswordEntity password = user.getPasswordEntity();
        password.setPassword(encoder.encode(request.password()));

        // persist the user and password
        userService.persist(user);
    }
}
