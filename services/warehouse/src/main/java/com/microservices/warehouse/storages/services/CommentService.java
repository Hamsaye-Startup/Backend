package com.microservices.warehouse.storages.services;

import com.microservices.warehouse.storages.exceptions.NotFoundCommentException;
import com.microservices.warehouse.storages.exceptions.PersistCommentException;
import com.microservices.warehouse.storages.models.CommentEntity;
import com.microservices.warehouse.storages.models.StorageEntity;
import com.microservices.warehouse.storages.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This service class provides operations for managing comments related to storage entities.
 * It interacts with the repository layer to perform CRUD operations on comments.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    /**
     * The repository used for performing comment operations.
     * See {@link com.microservices.warehouse.storages.repositories.CommentRepository} for more details.
     */
    private final CommentRepository commentRepository;

    /**
     * Inserts a new comment for a specified storage.
     *
     * @param comment The CommentEntity to be inserted.
     * @param storage The StorageEntity associated with the comment.
     * @return The inserted CommentEntity.
     * @throws PersistCommentException If there is an error during the insertion process.
     * @since 1.0
     */
    public CommentEntity insertComment(CommentEntity comment, StorageEntity storage) {
        try {
            comment.setEnabled(false);
            comment.setStorage(storage);
            return commentRepository.save(comment);
        } catch (RuntimeException ex) {
            throw new PersistCommentException(
                    String.format(
                            "Comment by %s for storage [%s]",
                            comment.getCommentBy().toString(),
                            storage.getId()
                    )
            );
        }
    }

    /**
     * Updates an existing comment.
     *
     * @param comment The CommentEntity to be updated.
     * @return The updated CommentEntity.
     * @throws PersistCommentException If there is an error during the update process.
     * @since 1.0
     */
    public CommentEntity updateComment(CommentEntity comment) {
        try {
            return commentRepository.save(comment);
        } catch (RuntimeException ex) {
            throw new PersistCommentException(
                    String.format(
                            "Comment by %s for storage [%s]",
                            comment.getCommentBy().toString(),
                            comment.getStorage().getId()
                    )
            );
        }
    }

    /**
     * Deletes a comment.
     *
     * @param comment The CommentEntity to be deleted.
     * @since 1.0
     */
    public void deleteComment(CommentEntity comment) {
        commentRepository.delete(comment);
    }

    /**
     * Finds a comment by its ID.
     *
     * @param commentId The ID of the comment to be found.
     * @return The found CommentEntity.
     * @throws NotFoundCommentException If the comment with the specified ID is not found.
     * @since 1.0
     */
    public CommentEntity findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException(commentId.toString()));
    }

    /**
     * Finds all comments associated with a specified storage entity.
     *
     * @param storage The StorageEntity for which comments are to be retrieved.
     * @param pageable Pagination information.
     * @return A page of CommentEntity objects.
     * @since 1.0
     */
    public Page<CommentEntity> findAllCommentsByStorage(StorageEntity storage, Pageable pageable) {
        return commentRepository.findAllByStorage(storage, pageable);
    }

    /**
     * Finds all comments associated with a specified storage entity that are either enabled or disabled.
     *
     * @param storage The StorageEntity for which comments are to be retrieved.
     * @param enabled The flag indicating whether to filter enabled or disabled comments.
     * @param pageable Pagination information.
     * @return A page of CommentEntity objects.
     * @since 1.0
     */
    public Page<CommentEntity> findAllCommentsByStorage(StorageEntity storage, boolean enabled, Pageable pageable) {
        return commentRepository.findAllByStorageAndEnabled(storage, enabled, pageable);
    }
}
