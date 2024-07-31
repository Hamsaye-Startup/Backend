package com.microservices.reservation.applications.scopes;

import lombok.Getter;

@Getter
public enum RequestScopeEnum {
    AUTHORIZATION("authorization"),
    LIMITED("customer"),
    FULL("god_vision");

    private final String scope;
    RequestScopeEnum(String scope) {
        this.scope = scope;
    }

}
