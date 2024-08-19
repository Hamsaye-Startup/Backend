package com.hamsaye.chat.messages.models;

import lombok.Builder;

@Builder
public record MultipartMetadata(
        String file
) {
}
