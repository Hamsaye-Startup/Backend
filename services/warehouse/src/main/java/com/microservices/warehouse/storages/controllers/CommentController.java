package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.storages.requests.CommentRequest;
import com.microservices.warehouse.storages.responses.CommentResponse;
import com.microservices.warehouse.storages.services.CommentServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/storage/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceManagement commentServiceManagement;
    private final MessageMapper mapper;

    @PostMapping("/storage/id/{storageId}")
    public ResponseEntity<?> addComment(
            @PathVariable("storageId") Long storageId,
            @RequestBody CommentRequest commentRequest
    ) {
        CommentResponse response = commentServiceManagement.addComment(
                storageId,
                commentRequest
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping("/id/{commentId}/display")
    public ResponseEntity<?> showComment(
            @PathVariable("commentId") Long id,
            @RequestParam(name = "displayable") boolean enabled
    ) {
        CommentResponse response = commentServiceManagement.displayComment(id, enabled);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/id/{commentId}")
    public ResponseEntity<?> removeComment(
            @PathVariable("commentId") Long id
    ) {
        CommentResponse response = commentServiceManagement.deleteCommentById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /*
    * find all legal comments for customers
    * */
    @GetMapping("/public/storage/id/{storageId}")
    public ResponseEntity<?> findAllDisplayableComments(
            @PathVariable("storageId") Long storageId,
            Pageable pageable
    ) {
        Page<CommentResponse> responses = commentServiceManagement.findAllCommentsByStorageId(
                storageId,
                true,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /*
    * find all comments
    * */
    @GetMapping("/storage/id/{storageId}")
    public ResponseEntity<?> findAllComments(
            @PathVariable("storageId") Long storageId,
            Pageable pageable
    ) {
        Page<CommentResponse> responses = commentServiceManagement.findAllCommentsByStorageId(
                storageId,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
