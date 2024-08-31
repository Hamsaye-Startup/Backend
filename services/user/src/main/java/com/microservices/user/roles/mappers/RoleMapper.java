package com.microservices.user.roles.mappers;

import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public RoleEntity toRoleEntity(RoleRequest request, RoleEntity role) {
        role.setName(request.name());
        role.setAuthorities(request.authorities());
        return role;
    }

    public RoleEntity toRoleEntity(RoleRequest request) {
        return RoleEntity.builder()
                .name(request.name())
                .authorities(request.authorities())
                .build();
    }

    public RoleResponse toResponse(RoleEntity role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .createAt(role.getCreatedAt())
                .authorities(role.getAuthorities())
                .build();
    }
}
