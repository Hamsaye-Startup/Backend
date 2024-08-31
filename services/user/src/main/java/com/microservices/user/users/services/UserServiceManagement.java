package com.microservices.user.users.services;

import com.microservices.user.kafka.producers.UserProducerService;
import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.services.RoleService;
import com.microservices.user.users.mappers.UserMapper;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserNotifyRequest;
import com.microservices.user.users.requests.UserNotifyType;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceManagement {

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
    public UserResponse update(UUID userId, UserRequest request, boolean admin) {

        // find the user by uid
        UserEntity exist = userService.findByUid(userId);

        // update the user information
        UserEntity user = mapper.toUserEntity(request, exist);

        UserEntity updated;
        if (admin) {

            // find the role by id
            RoleEntity role = roleService.findRoleById(request.rid());
            updated = userService.update(user, role);
        }
        else {
            updated = userService.update(user);
        }

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
    public Page<UserResponse> findAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable)
                .map(mapper::toResponse);
    }

    public UserResponse findById(UUID uid) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);
        return mapper.toResponse(user);
    }

    public UserResponse blockUser(UUID uid, boolean unblock) {

        // find the user by uid
        UserEntity user = userService.findByUid(uid);

        // update the user
        user.setEnabled(unblock);
        UserEntity updated = userService.update(user);

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
