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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentEntity insertComment(CommentEntity comment, StorageEntity storage) {
        try {
            comment.setEnabled(false);
            comment.setStorage(storage);
            return commentRepository.save(comment);
        } catch (RuntimeException ex) {
            throw new PersistCommentException(
                    String.format(
                            "comment by %s for storage[%s]",
                            comment.getCommentBy().toString(),
                            storage.getId()
                    )
            );
        }
    }

    public CommentEntity updateComment(CommentEntity comment) {
        try {
            return commentRepository.save(comment);
        } catch (RuntimeException ex) {
            throw new PersistCommentException(
                    String.format(
                            "comment by %s for storage[%s]",
                            comment.getCommentBy().toString(),
                            comment.getStorage().getId()
                    )
            );
        }
    }

    public void deleteCommentById(CommentEntity comment) {
        commentRepository.delete(comment);
    }

    public CommentEntity findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException(commentId.toString()));
    }

    public Page<CommentEntity> findAllCommentsByStorage(StorageEntity storage, Pageable pageable) {
        return commentRepository.findAllByStorage(storage, pageable);
    }

    public Page<CommentEntity> findAllCommentsByStorage(StorageEntity storage, boolean enabled, Pageable pageable) {
        return commentRepository.findAllByStorageAndEnabled(storage, enabled, pageable);
    }
}
