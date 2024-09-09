package com.microservices.user.users.mappers;

import com.microservices.user.users.dto.UserDTO;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.RegistrationRequest;
import com.microservices.user.users.requests.UserRequest;
import com.microservices.user.users.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping user-related data between different layers and representations.
 * It provides methods to convert between {@link UserEntity}, {@link UserRequest}, {@link RegistrationRequest},
 * {@link UserResponse}, and {@link UserDTO}.
 *
 * <p>This mapper is responsible for transforming incoming user data from requests into entity objects,
 * and vice versa, mapping entity data to response or DTO objects.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserMapper {

    /**
     * Maps a {@link UserRequest} to an existing {@link UserEntity}.
     * Updates the user entity's phone, first name, and last name based on the request data.
     *
     * @param request The {@link UserRequest} containing updated user information.
     * @param exist The existing {@link UserEntity} to be updated.
     * @return The updated {@link UserEntity}.
     * @since 1.0
     */
    public UserEntity toUserEntity(UserRequest request, UserEntity exist) {
        exist.setPhone(request.phone());
        exist.setFirstname(request.firstname());
        exist.setLastname(request.lastname());
        return exist;
    }

    /**
     * Converts a {@link RegistrationRequest} into a new {@link UserEntity}.
     *
     * @param request The {@link RegistrationRequest} containing the registration data.
     * @return A new {@link UserEntity} created based on the registration data.
     * @since 1.0
     */
    public UserEntity toUserEntity(RegistrationRequest request) {
        return UserEntity.builder()
                .phone(request.phone())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
    }

    /**
     * Maps a {@link UserEntity} to a {@link UserResponse}.
     * This is typically used to return user data as part of an API response.
     *
     * @param user The {@link UserEntity} to be converted.
     * @return A {@link UserResponse} containing user information.
     * @since 1.0
     */
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

    /**
     * Maps a {@link UserEntity} to a {@link UserDTO}.
     *
     * @param user The {@link UserEntity} to be converted.
     * @return A {@link UserDTO} containing the user details for data transfer purposes.
     * @since 1.0
     */
    public UserDTO toUserDTO(UserEntity user) {
        return UserDTO.builder()
                .uid(user.getUid())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .profilePictureId(null) // TODO: Placeholder for future implementation
                .build();
    }
}
