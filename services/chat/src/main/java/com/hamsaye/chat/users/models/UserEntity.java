package com.hamsaye.chat.users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * Entity class representing a user in the chat application.
 * <p>
 * This entity is stored in the MongoDB collection "col_user" and contains details about the user,
 * such as personal information, connection state, contact information, and other attributes like loyalty status.
 * It also tracks user-related version control for features like load balancing.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "col_user")
public class UserEntity {

    @Id
    private UUID uid;

    @Field("first_name")
    private String firstname;

    @Field("last_name")
    private String lastname;

    /**
     * ID representing the user's profile picture.
     */
    @Field("profile_picture_id")
    private UUID profilePictureId;

    /**
     * User's loyalty status, representing whether they are a new user or a loyal user.
     * @see com.hamsaye.chat.users.models.UserLoyaltyStatus for more details.
     */
    @Field("loyalty_status")
    private UserLoyaltyStatus loyaltyStatus;

    /**
     * User's contact information, such as phone numbers and addresses.
     * @see com.hamsaye.chat.users.models.UserContactInfo for more details.
     */
    private UserContactInfo contact;

    /**
     * User attributes, such as gender and other features.
     * @see com.hamsaye.chat.users.models.UserAttribute for more details.
     */
    private UserAttribute attribute;

    /**
     * Version control information for load balancing and other system features.
     * @see com.hamsaye.chat.users.models.VersionControl for more details.
     */
    @Field("version_control")
    private VersionControl versionControl;

    /**
     * The current connection state of the user (e.g., connected or disconnected).
     * @see com.hamsaye.chat.users.models.ConnectionStatus for more details.
     */
    @Field("connection_state")
    private UserConnectionState connectionState;
}
