package com.microservices.user.users.mapper;

import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toUserEntity(UserRequest request) {
        return UserEntity.builder()
                .uid(request.uid())
                .phone(request.phone())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
    }

    public UserEntity toUserEntity(RegistrationRequest request) {
        return UserEntity.builder()
                .phone(request.phone())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
    }

    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .uid(user.getUid())
                .phone(user.getPhone())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .rid(user.getRole().getId())
                .createAt(user.getCreatedAt())
                .build();
    }
}
