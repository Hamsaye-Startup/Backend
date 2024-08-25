package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.kafka.requests.CustomerNotifyRequest;
import com.hamsaye.chat.kafka.requests.CustomerNotifyType;
import com.hamsaye.chat.kafka.requests.UserNotifyRequest;
import com.hamsaye.chat.kafka.requests.UserNotifyType;
import com.hamsaye.chat.users.mappers.UserMapper;
import com.hamsaye.chat.users.models.UserAttribute;
import com.hamsaye.chat.users.models.UserContactInfo;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.models.UserGender;
import com.hamsaye.chat.users.models.UserLoyaltyStatus;
import com.hamsaye.chat.users.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerConsumerService {

    private final UserService userService;
    private final UserMapper userMapper;

    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseMessageMapper mapper;

    @KafkaListener(
            id = "external-user-info-listener-id",
            topics = "topic-general-customers",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void generalUserListener(
            UserNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "external-user-info-listener-id",
                request
        );

        if (request.type().equals(UserNotifyType.NEW_USER)) {

            // generate the user
            UserEntity user = generateUserByUserNotification(request);
            UserEntity saved = userService.saveUser(user);

            // update the websocket
            messagingTemplate.convertAndSend(
                    "/topic/users",
                    ResponseEntity.ok(mapper.toResponse(userMapper.toResponse(saved)))
            );

        } else if (request.type().equals(UserNotifyType.UPDATE_USER)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.userInfo().uid());

            // update the user information
            UserEntity updated = updateUserByUserNotification(user, request);
            userService.updateUser(updated);

        } else if (request.type().equals(UserNotifyType.DELETE_USER)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.userInfo().uid());
            userService.deleteUser(user);

        }
    }

    @KafkaListener(
            id = "external-user-detail-info-listener-id",
            topics = "topic-general-customer-details",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void userDetailListener(
            CustomerNotifyRequest request,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "external-user-info-listener-id",
                request
        );

        if (request.type().equals(CustomerNotifyType.NEW_USER) || request.type().equals(CustomerNotifyType.UPDATE_USER_INFO)) {

            // find the user by id
            UserEntity user = userService.findUserById(request.customerInfo().uid());

            // update the user information
            UserEntity updated = updateUserByCustomerNotification(user, request);
            userService.updateUser(updated);

        }
    }

    private UserEntity generateUserByUserNotification(UserNotifyRequest request) {
        return UserEntity.builder()
                .uid(request.userInfo().uid())
                .firstname(request.userInfo().firstname())
                .lastname(request.userInfo().lastname())
                .contact(UserContactInfo.builder()
                        .phone(request.userInfo().phone())
                        .build())
                .profilePictureId(request.userInfo().profilePictureId())
                .build();
    }

    private UserEntity updateUserByUserNotification(UserEntity user, UserNotifyRequest request) {
        user.setFirstname(request.userInfo().firstname());
        user.setLastname(request.userInfo().lastname());
        user.setContact(UserContactInfo.builder()
                .phone(request.userInfo().phone())
                .build());
        user.setProfilePictureId(request.userInfo().profilePictureId());
        return user;
    }

    private UserEntity updateUserByCustomerNotification(UserEntity user, CustomerNotifyRequest request) {
        user.setAttribute(UserAttribute.builder()
                .gender(UserGender.valueOf(request.customerInfo().genderEnum().name()))
                .build());
        user.setLoyaltyStatus(UserLoyaltyStatus.valueOf(request.customerInfo().loyaltyStatus().name()));
        return user;
    }
}
