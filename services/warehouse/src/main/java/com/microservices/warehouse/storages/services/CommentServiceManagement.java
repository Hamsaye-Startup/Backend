package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.mappers.CommentMapper;
import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.Score;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This service class provides operations for managing comments related to storage entities.
 * It interacts with the CommentController to handle comment-related requests and perform operations
 * such as adding, updating, deleting, and retrieving comments.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentServiceManagement {

    /**
     * The mapper used for converting between CommentRequest/CommentResponse and CommentEntity.
     * See {@link com.microservices.warehouse.storages.mappers.CommentMapper} for more details.
     */
    private final CommentMapper commentMapper;

    /**
     * The service used for performing CRUD operations on comments.
     * See {@link com.microservices.warehouse.storages.services.CommentService} for more details.
     */
    private final CommentService commentService;

    /**
     * The service used for performing CRUD operations on storage entities.
     * See {@link com.microservices.warehouse.storages.services.StorageService} for more details.
     */
    private final StorageService storageService;

    /**
     * Adds a new comment to a specified storage entity.
     *
     * @param storageId The ID of the storage to which the comment is added.
     * @param commentRequest The request object containing comment details.
     * @return The response object containing information about the added comment.
     * @since 1.0
     */
    public CommentResponse addComment(Long storageId, CommentRequest commentRequest) {

        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        // Convert the comment request to comment entity
        CommentEntity comment = commentMapper.toComment(commentRequest);

        // Insert the comment and update the storage score
        CommentEntity inserted = commentService.insertComment(comment, storage);
        incrementStorageScore(storage, inserted.getScore());
        storageService.updateStorage(storage);

        return commentMapper.toResponse(inserted);
    }

    /**
     * Updates the display status of a comment (enabled or disabled).
     *
     * @param id The ID of the comment to be updated.
     * @param enabled The flag indicating whether the comment should be displayed.
     * @return The response object containing information about the updated comment.
     * @since 1.0
     */
    public CommentResponse displayComment(Long id, boolean enabled) {

        // Find the comment by ID
        CommentEntity comment = commentService.findCommentById(id);

        // Update the comment's enabled flag
        comment.setEnabled(enabled);
        return commentMapper.toResponse(commentService.updateComment(comment));
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param id The ID of the comment to be deleted.
     * @return The response object containing information about the deleted comment.
     * @since 1.0
     */
    public CommentResponse deleteCommentById(Long id) {

        // Find the comment by ID
        CommentEntity comment = commentService.findCommentById(id);

        // Delete the comment and update the storage score
        commentService.deleteComment(comment);
        StorageEntity storage = comment.getStorage();
        reduceStorageScore(storage, comment.getScore());
        storageService.updateStorage(storage);

        return commentMapper.toResponse(comment);
    }

    /**
     * Retrieves all comments associated with a specified storage entity.
     *
     * @param storageId The ID of the storage for which comments are to be retrieved.
     * @param pageable Pagination information.
     * @return A page of response objects containing comment information.
     * @since 1.0
     */
    public Page<CommentResponse> findAllCommentsByStorageId(
            Long storageId,
            Pageable pageable
    ) {
        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        return commentService.findAllCommentsByStorage(storage, pageable)
                .map(commentMapper::toResponse);
    }

    /**
     * Retrieves all comments associated with a specified storage entity, filtered by display status.
     *
     * @param storageId The ID of the storage for which comments are to be retrieved.
     * @param enabled The flag indicating whether to include enabled comments only.
     * @param pageable Pagination information.
     * @return A page of response objects containing comment information.
     * @since 1.0
     */
    public Page<CommentResponse> findAllCommentsByStorageId(
            Long storageId,
            boolean enabled,
            Pageable pageable
    ) {
        // Find the storage by ID
        StorageEntity storage = storageService.findStorageById(storageId);

        return commentService.findAllCommentsByStorage(storage, enabled, pageable)
                .map(commentMapper::toResponse);
    }

    /**
     * Increments the storage's score based on the comment's score.
     *
     * @param storage The StorageEntity to be updated.
     * @param point The score of the comment to be added.
     * @since 1.0
     */
    private void incrementStorageScore(StorageEntity storage, Float point) {
        Score score = storage.getScore();
        int votes = score.getVotes() + 1;
        float average = (score.getScore() * score.getVotes() + point) / votes;

        storage.setScore(Score.builder()
                .score(average)
                .votes(votes)
                .build());
    }

    /**
     * Reduces the storage's score based on the comment's score.
     *
     * @param storage The StorageEntity to be updated.
     * @param point The score of the comment to be subtracted.
     * @since 1.0
     */
    private void reduceStorageScore(StorageEntity storage, Float point) {
        Score score = storage.getScore();
        int votes = score.getVotes() - 1;
        float average = (score.getScore() * score.getVotes() - point) / votes;

        storage.setScore(Score.builder()
                .score(average)
                .votes(votes)
                .build());
    }
}
