package com.microservices.user.roles.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Enum representing various user authorities and permissions.
 * This enum implements {@link GrantedAuthority} to integrate with Spring Security's authorization system.
 * Each constant in this enum represents a specific permission or authority that can be granted to a user.
 *
 * @see GrantedAuthority
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Getter
public enum UserAuthorityEnum implements GrantedAuthority, Serializable {

    /**
     * Permission to update a user.
     */
    UPDATE_USER("UPDATE_USER"),

    /**
     * Permission to delete a user.
     */
    DELETE_USER("DELETE_USER"),

    /**
     * Permission to block a user.
     */
    BLOCK_USER("BLOCK_USER");

    private final String permissions;

    UserAuthorityEnum(String permissions) {
        this.permissions = permissions;
    }

    /**
     * Returns the authority string of this enum constant.
     *
     * @return The permission string associated with this authority.
     */
    @Override
    public String getAuthority() {
        return this.permissions;
    }
}
