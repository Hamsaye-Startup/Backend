package com.microservices.user.users.mappers;

import com.microservices.user.users.dto.UserDetailDTO;
import com.microservices.user.users.models.GenderEnum;
import com.microservices.user.users.models.UserDetailEntity;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.requests.UserDetailRequest;
import com.microservices.user.users.responses.UserDetailResponse;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Mapper class for converting between {@link UserDetailEntity}, {@link UserDetailDTO}, {@link UserDetailRequest},
 * and {@link UserDetailResponse} objects. This class handles the mapping of data between the user detail model,
 * data transfer objects, and request/response objects used in the application.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailMapper {

    private final UserService userService;

    /**
     * Converts a {@link UserDetailEntity} to a {@link UserDetailEntity}, updating an existing user entity.
     *
     * @param request  the request object containing updated user details.
     * @return the updated {@link UserDetailEntity}.
     * @since 1.0
     */
    public UserDetailEntity toUserDetail(UserDetailRequest request, UUID userId) {

        UserEntity user = userService.findByUid(userId);

        return UserDetailEntity.builder()
                .nid(request.nid())
                .bio(request.bio())
                .gender(convertGender(request.gender()))
                .user(user)
                .build();
    }

    public UserDetailEntity toUserDetail(UserDetailRequest request, UserEntity user) {
        return UserDetailEntity.builder()
                .nid(request.nid())
                .bio(request.bio())
                .gender(convertGender(request.gender()))
                .user(user)
                .build();
    }

    /**
     * Converts a {@link UserDetailEntity} to a {@link UserDetailResponse}.
     *
     * @param user the {@link UserDetailEntity} to be converted.
     * @return the corresponding {@link UserDetailResponse}.
     * @since 1.0
     */
    public UserDetailResponse toResponse(UserDetailEntity user) {
        return UserDetailResponse.builder()
                .uid(user.getUser().getUid())
                .nid(user.getNid())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .gender(user.getGender())
                .loyalty(user.getLoyalty())
                .connection(user.getConnectionStatus())
                .build();
    }

    /**
     * Converts a {@link UserDetailEntity} to a {@link UserDetailDTO}.
     *
     * @param user the {@link UserDetailEntity} to be converted.
     * @return the corresponding {@link UserDetailDTO}.
     * @since 1.0
     */
    public UserDetailDTO toUserDetailDTO(UserDetailEntity user) {
        return UserDetailDTO.builder()
                .uid(user.getUser().getUid())
                .gender(user.getGender())
                .loyalty(user.getLoyalty())
                .build();
    }

    public GenderEnum convertGender(String gender) {
        try {
            return GenderEnum.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException ex) {

            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(GenderEnum.class),
                    gender,
                    ex.getCause()
            );
        }
    }
}
