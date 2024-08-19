package com.hamsaye.chat.users.models;

import lombok.Builder;

@Builder
public record UserContactInfo(
        String phone,
        String address

        // private String location; // for next version;
) {
}
