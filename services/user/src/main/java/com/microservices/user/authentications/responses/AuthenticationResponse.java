package com.microservices.user.authentications.responses;

import lombok.Builder;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record AuthenticationResponse(
        String accessToken,
        int expiredIn,
        String refresh,
        String tokenType

) {
}
