package com.microservices.warehouse.warehouses.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.warehouses.requests.CommentRequest;
import com.microservices.warehouse.warehouses.responses.CommentResponse;
import com.microservices.warehouse.warehouses.services.CommentServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment/storage")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceManagement management;
    private final MessageMapper mapper;

    @PostMapping("/id/{storageId}")
    public ResponseEntity<?> addComment(
            @PathVariable("storageId") Long id,
            @RequestBody CommentRequest commentRequest
    ) {
        CommentResponse response = management.addComment(id, commentRequest);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/id/{storageId}")
    public ResponseEntity<?> showAllComments(
            @PathVariable("storageId") Long id,
            Pageable pageable
    ) {
        Page<CommentResponse> responses = management.findAllCommentsByWarehouseId(id, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
