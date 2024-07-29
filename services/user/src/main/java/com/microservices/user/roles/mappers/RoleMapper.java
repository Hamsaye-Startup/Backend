package com.microservices.user.roles.mappers;

import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.requests.NewRollRequest;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    public RoleEntity toRoleEntity(RoleRequest request, RoleEntity role) {
        return RoleEntity.builder()
                .id(request.uid())
                .name(request.name() == null ? role.getName() : request.name())
                .authorities(request.authorities() == null ? role.getAuthorities() : request.authorities())
                .build();
    }

    public RoleEntity toRoleEntity(NewRollRequest request) {
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
