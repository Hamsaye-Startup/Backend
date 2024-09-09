package com.microservices.user.roles.mappers;

import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import org.springframework.stereotype.Service;

/**
 * Provides methods for mapping between {@link RoleRequest} and {@link RoleEntity},
 * and for converting {@link RoleEntity} to {@link RoleResponse}.
 * This service helps in transforming data between different layers of the application.
 *
 * @see RoleRequest
 * @see RoleEntity
 * @see RoleResponse
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
public class RoleMapper {

    /**
     * Maps a {@link RoleRequest} to a {@link RoleEntity}, updating the existing role entity.
     *
     * @param request The role request containing data to be mapped.
     * @param role The existing role entity to be updated.
     * @return The updated {@link RoleEntity}.
     */
    public RoleEntity toRoleEntity(RoleRequest request, RoleEntity role) {
        role.setName(request.name());
        role.setAuthorities(request.authorities());
        return role;
    }

    /**
     * Maps a {@link RoleRequest} to a new {@link RoleEntity}.
     *
     * @param request The role request containing data to be mapped.
     * @return A new {@link RoleEntity} with values from the request.
     */
    public RoleEntity toRoleEntity(RoleRequest request) {
        return RoleEntity.builder()
                .name(request.name())
                .authorities(request.authorities())
                .build();
    }

    /**
     * Converts a {@link RoleEntity} to a {@link RoleResponse}.
     *
     * @param role The role entity to be converted.
     * @return A {@link RoleResponse} with values from the role entity.
     */
    public RoleResponse toResponse(RoleEntity role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .createAt(role.getCreatedAt())
                .authorities(role.getAuthorities())
                .build();
    }
}
