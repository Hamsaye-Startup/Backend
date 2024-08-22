package com.microservices.warehouse.warehouses.mappers;

import com.microservices.warehouse.warehouses.models.CommentEntity;
import com.microservices.warehouse.warehouses.responses.CommentResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    public CommentResponse toResponse(CommentEntity comment) {
        return CommentResponse.builder()
                .commentBy(comment.getCommentBy())
                .score(comment.getScore())
                .content(comment.getContent())
                .commentAt(comment.getCommentAt())
                .build();
    }
}
