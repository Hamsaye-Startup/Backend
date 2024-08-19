package com.microservices.user.users.services;

import com.microservices.user.application.exceptions.NotFoundScopeException;
import com.microservices.user.kafka.producers.UserProducerService;
import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.services.RoleService;
import com.microservices.user.application.scopes.RequestScopeEnum;
import com.microservices.user.application.scopes.ScopeDetector;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.mappers.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserNotifyRequest;
import com.microservices.user.users.requests.UserNotifyType;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

    private final UserProducerService userProducerService;

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

        UserEntity persisted = userService.persist(user, customerRole, password);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(persisted))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(persisted);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserResponse update(UserRequest request, String scope, Principal principal) {

        // find the user by uid
        UserEntity user = userService.findByUid(request.uid());

        // update the user information
        user.setPhone(request.phone());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());

        // check the scope
        if (scopeDetector.detected(scope, RequestScopeEnum.FULL.getScope())) {

            // fetch the role
            RoleEntity role = roleService.findRoleById(request.rid());

            // update the user
            UserEntity updated = userService.update(user, role, user.isEnabled());

            // send a notification
            userProducerService.send(
                    UserNotifyRequest.builder()
                            .userInfo(mapper.toUserDTO(updated))
                            .message(UserNotifyType.NEW_USER.getMessage())
                            .type(UserNotifyType.NEW_USER)
                            .build()
            );

            return mapper.toResponse(updated);

        } else if (scopeDetector.detected(scope, RequestScopeEnum.LIMITED.getScope())) {

            // check the principal user
            if (principal.getName().equals(user.getUid().toString())) {

                // update the user
                UserEntity updated = userService.update(user, user.isEnabled());

                // send a notification
                userProducerService.send(
                        UserNotifyRequest.builder()
                                .userInfo(mapper.toUserDTO(updated))
                                .message(UserNotifyType.NEW_USER.getMessage())
                                .type(UserNotifyType.NEW_USER)
                                .build()
                );

                return mapper.toResponse(updated);

            } else {
                throw new IllegalRequestException(request.uid().toString(), principal.getName());
            }

        }
        throw new NotFoundScopeException(request.uid().toString());
    }

    public UserResponse delete(UUID uid) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // delete the user
        UserEntity deleted = userService.delete(user);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(deleted))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(deleted);
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

    public UserResponse blockUser(UUID uid, boolean unblock) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // update the user
        UserEntity updated = userService.update(user, unblock);

        // send a notification
        userProducerService.send(
                UserNotifyRequest.builder()
                        .userInfo(mapper.toUserDTO(updated))
                        .message(UserNotifyType.NEW_USER.getMessage())
                        .type(UserNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(updated);
    }
}
