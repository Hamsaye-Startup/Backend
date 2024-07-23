package com.microservices.user.authentications.responses;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String accessToken,
        int expiredIn,
        String refresh,
        String tokenType,
        String scope

) {
}
