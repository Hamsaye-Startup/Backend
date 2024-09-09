package com.hamsaye.chat.users.requests;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserRequest(

        UUID uid,
        String firstname,
        String lastname,
        String phone,
        UUID profilePictureId

) implements Serializable {
}
