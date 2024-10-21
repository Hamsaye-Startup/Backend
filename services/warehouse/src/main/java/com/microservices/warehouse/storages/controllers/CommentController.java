package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.application.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.application.mapper.MessageMapper;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import com.microservices.warehouse.storages.services.CommentServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This controller handles HTTP requests related to comments on storages.
 * It interacts with the {@link CommentServiceManagement} to perform operations on comments.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage/comment")
@RequiredArgsConstructor
public class CommentController {

    /**
     * Service management for handling comment operations.
     * See {@link com.microservices.warehouse.storages.services.CommentServiceManagement} for more details.
     */
    private final CommentServiceManagement commentServiceManagement;

    /**
     * Mapper for transforming between different representations of comments.
     * See {@link com.microservices.warehouse.application.mapper.MessageMapper} for more details.
     */
    private final MessageMapper mapper;

    /**
     * Extracts the user ID from the HTTP request header, throwing an exception if not found.
     * @param request the HTTP request containing the user ID header
     * @return the extracted user ID as a {@link UUID}
     * @throws AuthenticationCredentialNotFoundException if the user ID header is missing
     * @since 1.0
     */
    private UUID findUserByHeaderOrThrow(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    /**
     * Extracts the user ID from the HTTP request header, returning null if not found.
     * @param request the HTTP request containing the user ID header
     * @return the extracted user ID as a {@link UUID}, or null if the header is missing
     * @since 1.0
     */
    private UUID findUserByHeaderOrNull(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            return null;
        }
        return UUID.fromString(userId);
    }

    /**
     * Adds a new comment to a storage item.
     * @param storageId the ID of the storage to add the comment to
     * @param commentRequest the request object containing the comment details
     * @return ResponseEntity containing the added comment information
     * @since 1.0
     */
    @PostMapping("/storage/id/{storageId}")
    public ResponseEntity<?> addComment(
            @PathVariable("storageId") Long storageId,
            @RequestBody CommentRequest commentRequest,
            HttpServletRequest request
    ) {
        CommentResponse response = commentServiceManagement.addComment(
                storageId,
                commentRequest
        );
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Updates the display status of a comment.
     * @param id the ID of the comment to update
     * @param enabled flag indicating whether the comment should be displayed
     * @return ResponseEntity containing the updated comment information
     * @since 1.0
     */
    @PutMapping("/id/{commentId}/display")
    public ResponseEntity<?> showComment(
            @PathVariable("commentId") Long id,
            @RequestParam(name = "displayable") boolean enabled,
            HttpServletRequest request
    ) {
        CommentResponse response = commentServiceManagement.displayComment(id, enabled);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Removes a comment by its ID.
     * @param id the ID of the comment to remove
     * @return ResponseEntity containing the deleted comment information
     * @since 1.0
     */
    @DeleteMapping("/id/{commentId}")
    public ResponseEntity<?> removeComment(
            @PathVariable("commentId") Long id,
            HttpServletRequest request
    ) {
        CommentResponse response = commentServiceManagement.deleteCommentById(id);
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds all displayable comments for a specific storage item.
     * @param storageId the ID of the storage to find comments for
     * @param pageable pagination information
     * @return ResponseEntity containing a page of displayable comments for the storage
     * @since 1.0
     */
    @GetMapping("/public/storage/id/{storageId}")
    public ResponseEntity<?> findAllDisplayableComments(
            @PathVariable("storageId") Long storageId,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<CommentResponse> responses = commentServiceManagement.findAllCommentsByStorageId(
                storageId,
                true,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds all comments for a specific storage item, regardless of display status.
     * @param storageId the ID of the storage to find comments for
     * @param pageable pagination information
     * @return ResponseEntity containing a page of comments for the storage
     * @since 1.0
     */
    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllComments(
            @PathVariable("storageId") Long storageId,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<CommentResponse> responses = commentServiceManagement.findAllCommentsByStorageId(
                storageId,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }
}
