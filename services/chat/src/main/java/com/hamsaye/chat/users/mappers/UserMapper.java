package com.hamsaye.chat.users.mappers;

import com.hamsaye.chat.users.models.UserContactInfo;
import com.hamsaye.chat.users.models.UserDTO;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserRequest;
import com.hamsaye.chat.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toUser(UserRequest request) {
        return UserEntity.builder()
                .uid(request.uid())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .profilePictureId(request.profilePictureId())
                .contact(UserContactInfo.builder()
                        .phone(request.phone())
                        .build())
                .build();
    }

    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .uid(user.getUid())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profilePictureId(user.getProfilePictureId())
                .connectionState(user.getConnectionState())
                .build();
    }

    public UserDTO toDTO(UserEntity user) {
        return UserDTO.builder()
                .uid(user.getUid())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profilePictureId(user.getProfilePictureId())
                .connectionState(user.getConnectionState())
                .contact(user.getContact())
                .userAttribute(user.getAttribute())
                .build();
    }
}
