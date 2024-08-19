package com.hamsaye.chat.converstions.models;

import com.hamsaye.chat.users.models.UserRef;
import lombok.Builder;

@Builder
public record UserReferenceCollection(
        UserRef starter,
        UserRef continuator
) {
}
