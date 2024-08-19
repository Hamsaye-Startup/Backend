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

    @Field("profile_picture_id")
    private UUID profilePictureId;

    @Field("loyalty_status")
    private UserLoyaltyStatus loyaltyStatus; // new user or loyal

    private UserContactInfo contact; // phones and addresses

    private UserAttribute attribute; // features like gender and etc

    @Field("version_control")
    private VersionControl versionControl; // contain the all features for load balancing and etc

    @Field("connection_state")
    private UserConnectionState connectionState; // disconnected or connected
}
