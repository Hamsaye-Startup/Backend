package com.microservices.user.roles.services;

import com.microservices.user.roles.mappers.RoleMapper;
import com.microservices.user.roles.models.RoleEntity;
import com.microservices.user.roles.models.UserAuthorityEnum;
import com.microservices.user.roles.requests.RoleRequest;
import com.microservices.user.roles.responses.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing roles and interacting with the role controller.
 * This class provides methods to add, update, delete, and retrieve roles.
 * It also provides methods to retrieve all available authorities and authorities for a specific role.
 *
 * @see RoleRequest
 * @see RoleResponse
 * @see RoleService
 * @see RoleMapper
 * @see UserAuthorityEnum
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RoleServiceManagement {

    private final RoleService roleService;
    private final RoleMapper mapper;

    /**
     * Adds a new role based on the provided {@link RoleRequest}.
     *
     * @param request The request containing details of the role to be added.
     * @return A {@link RoleResponse} containing the added role's details.
     * @since 1.0
     */
    public RoleResponse add(RoleRequest request) {
        // generate the role entity and persist
        RoleEntity role = mapper.toRoleEntity(request);
        return mapper.toResponse(roleService.persist(role));
    }

    /**
     * Updates an existing role identified by {@code roleId} with the details provided in {@link RoleRequest}.
     *
     * @param roleId The ID of the role to be updated.
     * @param request The request containing updated details of the role.
     * @return A {@link RoleResponse} containing the updated role's details.
     * @since 1.0
     */
    public RoleResponse update(UUID roleId, RoleRequest request) {
        // check the role by uid
        RoleEntity role = roleService.findRoleById(roleId);
        RoleEntity newRole = mapper.toRoleEntity(request, role);
        return mapper.toResponse(roleService.persist(newRole));
    }

    /**
     * Deletes the role identified by {@code uid}.
     *
     * @param uid The ID of the role to be deleted.
     * @return A {@link RoleResponse} containing the deleted role's details.
     * @since 1.0
     */
    public RoleResponse delete(UUID uid) {
        // fetch the role by uid
        RoleEntity role = roleService.findRoleById(uid);
        return mapper.toResponse(roleService.delete(role));
    }

    /**
     * Retrieves all roles with pagination based on the provided {@link Pageable}.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link RoleResponse} containing role details.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<RoleResponse> findAllRoles(Pageable pageable) {
        return roleService.findAllRoles(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Retrieves the role identified by {@code uid}.
     *
     * @param uid The ID of the role to be retrieved.
     * @return A {@link RoleResponse} containing the details of the requested role.
     * @since 1.0
     */
    public RoleResponse findById(UUID uid) {
        return mapper.toResponse(roleService.findRoleById(uid));
    }

    /**
     * Retrieves all available authorities.
     *
     * @return A {@link Set} of strings representing all available authorities.
     * @since 1.0
     */
    public Set<String> findAllAuthorities() {
        return Set.of(UserAuthorityEnum.values()).stream()
                .map(UserAuthorityEnum::getPermissions)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves all authorities for the role identified by {@code uid}.
     *
     * @param uid The ID of the role whose authorities are to be retrieved.
     * @return A {@link Set} of strings representing authorities for the specified role.
     * @since 1.0
     */
    public Set<String> findAllAuthorities(UUID uid) {
        return roleService.findRoleById(uid).getAuthorities().stream()
                .map(UserAuthorityEnum::getPermissions)
                .collect(Collectors.toSet());
    }
}
