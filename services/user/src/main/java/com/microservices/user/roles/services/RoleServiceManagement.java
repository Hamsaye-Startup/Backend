package com.microservices.user.roles.services;

import com.microservices.user.roles.mappers.RoleMapper;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.models.UserAuthorityEnum;
import com.microservices.user.roles.requests.NewRollRequest;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceManagement {

    private final RoleService roleService;
    private final RoleMapper mapper;

    public RoleResponse add(NewRollRequest request) {

        // generate the role entity and persist
        RoleEntity role = mapper.toRoleEntity(request);
        return mapper.toResponse(roleService.persist(role));
    }

    public RoleResponse update(RoleRequest request) {

        // check the role by uid
        RoleEntity role = roleService.findRoleById(request.uid());

        RoleEntity newRole = mapper.toRoleEntity(request, role);
        return mapper.toResponse(roleService.persist(newRole));
    }

    public RoleResponse delete(UUID uid) {

        // fetch the role by uid
        RoleEntity role = roleService.findRoleById(uid);
        return mapper.toResponse(roleService.delete(role));
    }

    // find all roles based on timestamp
    // default value is the first 20 roles of list based on the creation date
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RoleResponse> findAllRoles(LocalDateTime offset) {

        // fetch the roles
        List<RoleEntity> roles;
        if (offset == null) {
            roles = roleService.findAllRoles();
        } else {
            roles = roleService.findAllRoles(offset);
        }

        return roles.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse findById(UUID uid) {
        return mapper.toResponse(roleService.findRoleById(uid));
    }

    public Set<UserAuthorityEnum> findAllAuthorities() {
        return Set.of(UserAuthorityEnum.values());
    }
}
