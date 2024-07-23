package com.microservices.user.roles.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
public enum UserAuthorityEnum implements GrantedAuthority, Serializable {
    UPDATE_USER("UPDATE_USER"),
    DELETE_USER("DELETE_USER"),
    READ_USERS("READ_USERS"),
    READ_USER("READ_USER"),
    BLOCK_USER("BLOCK_USER"),
    WRITE_ROLE("WRITE_ROLE"),
    DELETE_ROLE("DELETE_ROLE"),
    READ_ROLES("READ_ROLES"),
    READ_ROLE("READ_ROLE"),
    UPDATE_PASS("UPDATE_PASS"),
    RESET_PASS("RESET_PASS");

    private final String permissions;

    UserAuthorityEnum(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return this.permissions;
    }
}
