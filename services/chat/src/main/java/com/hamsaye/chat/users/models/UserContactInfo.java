package com.hamsaye.chat.users.models;

import lombok.Builder;

/**
 * Represents the contact information of a user in the chat system.
 * <p>
 * This record contains the user's phone number and address details.
 * Future versions may include additional fields such as location.
 * </p>
 *
 * @param phone   The user's phone number.
 * @param address The user's address.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserContactInfo(
        String phone,
        String address

        // private String location; // for next version;
) {
}
