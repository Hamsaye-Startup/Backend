package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.reservations.services.ReservationService;
import com.microservices.warehouse.storages.mappers.CommentMapper;
import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceManagement {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    private final StorageService storageService;

    public CommentResponse addComment(Long storageId, CommentRequest commentRequest) {

        // find the storage by id
        StorageEntity storage = storageService.findStorageById(storageId);

        // convert the comment request to comment entity
        CommentEntity comment = commentMapper.toComment(commentRequest);
        return commentMapper.toResponse(commentService.insertComment(comment, storage));
    }


    public CommentResponse displayComment(Long id, boolean enabled) {

        // find comment by id
        CommentEntity comment = commentService.findCommentById(id);

        comment.setEnabled(enabled);
        return commentMapper.toResponse(commentService.updateComment(comment));
    }

    public CommentResponse deleteCommentById(Long id) {

        // find comment by id
        CommentEntity comment = commentService.findCommentById(id);

        commentService.deleteCommentById(comment);
        return commentMapper.toResponse(comment);
    }

    /*
    * find all comments by high permission
    * */
    public Page<CommentResponse> findAllCommentsByStorageId(
            Long storageId,
            Pageable pageable
    ) {
        // find the storage by id
        StorageEntity storage = storageService.findStorageById(storageId);

        return commentService.findAllCommentsByStorage(storage, pageable)
                .map(commentMapper::toResponse);
    }

    public Page<CommentResponse> findAllCommentsByStorageId(
            Long storageId,
            boolean enabled,
            Pageable pageable
    ) {
        // find the storage by id
        StorageEntity storage = storageService.findStorageById(storageId);

        return commentService.findAllCommentsByStorage(storage, enabled, pageable)
                .map(commentMapper::toResponse);
    }
}
