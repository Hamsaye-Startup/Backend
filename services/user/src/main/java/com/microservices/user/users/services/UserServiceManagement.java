package com.microservices.user.users.services;

import com.microservices.user.application.exceptions.NotFoundScopeException;
import com.microservices.user.passwords.model.PasswordEntity;
import com.microservices.user.roles.model.RoleEntity;
import com.microservices.user.roles.services.RoleService;
import com.microservices.user.application.scopes.RequestScopeEnum;
import com.microservices.user.application.scopes.ScopeDetector;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.mapper.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceManagement {

    private final ScopeDetector scopeDetector;
    private final UserMapper mapper;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse register(RegistrationRequest userRequest) {

        // generate a user entity
        UserEntity user = mapper.toUserEntity(userRequest);

        // fetch the customer role
        RoleEntity customerRole = roleService.findRoleByName("role_default");

        // generate the password entity
        PasswordEntity password = PasswordEntity.builder()
                .password(passwordEncoder.encode(userRequest.password()))
                .expiredAt(LocalDateTime.now().plusDays(100))
                .build();

        user.setRole(customerRole);
        user.setPasswordEntity(password);
        user.setEnabled(true);

        // persist the user and map to response
        return mapper.toResponse(userService.persist(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse update(UserRequest request, String scope, Principal principal) {

        // find the user by uid
        UserEntity user = userService.findByUid(request.uid());
        user.setPhone(request.phone());

        // check the scope
        if (scopeDetector.detected(scope, RequestScopeEnum.FULL.getScope())) {
            // fetch the role
            RoleEntity role = roleService.findRoleById(request.rid());
            user.setRole(role);

            return mapper.toResponse(userService.persist(user));
        } else if (scopeDetector.detected(scope, RequestScopeEnum.LIMITED.getScope())) {
            // check the principal user
            if (principal.getName().equals(user.getUid().toString())) {
                return mapper.toResponse(userService.persist(user));
            } else {
                throw new IllegalRequestException(request.uid().toString(), principal.getName());
            }
        }
        throw new NotFoundScopeException(request.uid().toString());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse delete(UUID uid) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);
        return mapper.toResponse(userService.delete(user));
    }

    // find all users based on timestamp
    // default value is the first 20 users of list based on the creation date
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<UserResponse> findAllUsers(LocalDateTime offset) {

        // fetch the users
        List<UserEntity> users;
        if (offset == null) {
            users = userService.findAllUsers();
        } else {
            users = userService.findAllUsers(offset);
        }

        return users.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse findById(UUID uid, String scope, Principal principal) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // check the scope
        if (scopeDetector.detected(scope, RequestScopeEnum.FULL.getScope())) {
            if (principal.getName().equals(user.getUid().toString())) {
                return mapper.toResponse(user);
            } else {
                throw new IllegalRequestException(uid.toString(), principal.getName());
            }
        }
        throw new NotFoundScopeException(uid.toString());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse blockUser(UUID uid, boolean unblock) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);
        user.setEnabled(unblock);

        return mapper.toResponse(userService.persist(user));
    }
}
