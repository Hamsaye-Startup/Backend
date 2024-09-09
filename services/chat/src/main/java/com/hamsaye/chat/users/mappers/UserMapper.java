package com.hamsaye.chat.users.mappers;

import com.hamsaye.chat.users.models.UserContactInfo;
import com.hamsaye.chat.users.models.UserDTO;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.requests.UserRequest;
import com.hamsaye.chat.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for mapping between user-related data models and request/response objects.
 * <p>
 * This class provides methods to convert between different representations of user data, including
 * converting {@link UserRequest} to {@link UserEntity}, {@link UserEntity} to {@link UserResponse}, and
 * {@link UserEntity} to {@link UserDTO}.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserMapper {

    /**
     * Converts a {@link UserRequest} to a {@link UserEntity}.
     *
     * @param request the {@link UserRequest} object to convert
     * @return the corresponding {@link UserEntity}
     * @since 1.0
     */
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

    /**
     * Converts a {@link UserEntity} to a {@link UserResponse}.
     *
     * @param user the {@link UserEntity} object to convert
     * @return the corresponding {@link UserResponse}
     * @since 1.0
     */
    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .uid(user.getUid())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profilePictureId(user.getProfilePictureId())
                .connectionState(user.getConnectionState())
                .build();
    }

    /**
     * Converts a {@link UserEntity} to a {@link UserDTO}.
     *
     * @param user the {@link UserEntity} object to convert
     * @return the corresponding {@link UserDTO}
     * @since 1.0
     */
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
