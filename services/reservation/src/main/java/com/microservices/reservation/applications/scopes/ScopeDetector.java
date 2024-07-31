package com.microservices.reservation.applications.scopes;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ScopeDetector {

    public boolean detected(String scope, String value) {
        // check null pointer exception
        if (scope == null)
            return false;

        return Arrays.asList(scope.split(" ")).contains(value);
    }
}
