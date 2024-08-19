package com.hamsaye.chat.users.models;

import lombok.Builder;

@Builder
public record UserAttribute(
        UserGender gender
) {
}
