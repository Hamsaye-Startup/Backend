package com.microservices.user.users.services;

import com.microservices.user.kafka.requests.UserDetailNotifyRequest;
import com.microservices.user.kafka.requests.UserDetailNotifyType;
import com.microservices.user.kafka.producers.UserDetailProducerService;
import com.microservices.user.users.mappers.UserDetailMapper;
import com.microservices.user.users.models.UserConnectionStatus;
import com.microservices.user.users.models.UserDetailEntity;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.UserDetailRequest;
import com.microservices.user.users.responses.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for managing user detail operations including registration, updates, deletions, and retrieval.
 * This class interacts with the {@link UserDetailService} for persistence operations and the {@link UserDetailProducerService}
 * for sending notifications about user-related changes.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceManagement {

    private final UserDetailMapper mapper;
    private final UserDetailService service;
    private final UserDetailProducerService userDetailProducer;

    private final UserService userService;

    /**
     * Registers a new customer and sends a notification about the new registration.
     *
     * @param request the {@link UserDetailRequest} containing details of the new customer.
     * @return the {@link UserDetailResponse} for the newly registered customer.
     * @since 1.0
     */
    public UserDetailResponse register(UserDetailRequest request, UUID userId) {

        // generate a user detail
        UserDetailEntity user = mapper.toUserDetail(request, userId);

        // persist the user detail
        UserDetailEntity persistedUser = service.insert(user);

        // send a notification
        userDetailProducer.send(
                UserDetailNotifyRequest.builder()
                        .userInfo(mapper.toUserDetailDTO(persistedUser))
                        .message(UserDetailNotifyType.NEW_USER.getMessage())
                        .type(UserDetailNotifyType.NEW_USER)
                        .build()
        );

        return mapper.toResponse(persistedUser);
    }

    /**
     * Updates an existing user detail and sends a notification about the updated information.
     *
     * @param request the {@link UserDetailRequest} containing updated user details.
     * @return the {@link UserDetailResponse} for the updated user detail.
     * @since 1.0
     */
    public UserDetailResponse update(UserDetailRequest request, UUID userId) {

        UserEntity user = userService.findByUid(userId);

        // generate a user detail
        UserDetailEntity exists = service.findByUserId(user);
        UserDetailEntity userDetail = mapper.toUserDetail(request, user);

        // update the user detail information
        UserDetailEntity updatedCustomer = service.update(
                userDetail,
                exists
        );

        return mapper.toResponse(updatedCustomer);
    }

    /**
     * Deletes a user detail and sends a notification about the deletion.
     *
     * @param userId the UUID of the user to be deleted.
     * @return the {@link UserDetailResponse} for the deleted user detail.
     * @since 1.0
     */
    public UserDetailResponse deleteByUserId(UUID userId) {

        UserEntity user = userService.findByUid(userId);

        // fetch the user detail by uid
        UserDetailEntity userDetail = service.findByUserId(user);

        // delete the user detail information
        UserDetailEntity deletedUser = service.delete(userDetail);

        return mapper.toResponse(deletedUser);
    }

    /**
     * Finds a user detail by its unique ID.
     *
     * @param userId the UUID of the user to be retrieved.
     * @return the {@link UserDetailResponse} for the user detail with the specified ID.
     * @since 1.0
     */
    public UserDetailResponse findByUserId(UUID userId) {

        UserEntity user = userService.findByUid(userId);
        return mapper.toResponse(service.findByUserId(user));
    }

    public Page<UserDetailResponse> findAllOnlineUsersByRoleId(UUID rid, Pageable pageable) {
        return service.findAllByRoleIdAndConnectionStatus(rid, UserConnectionStatus.ONLINE, pageable)
                .map(mapper::toResponse);
    }
}
