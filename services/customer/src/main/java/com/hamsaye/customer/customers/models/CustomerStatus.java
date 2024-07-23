package com.hamsaye.customer.customers.models;

import lombok.Getter;

@Getter
public enum CustomerStatus {

    TEST("test");

    private final String value;

    CustomerStatus(String value) {
        this.value = value;
    }
}
