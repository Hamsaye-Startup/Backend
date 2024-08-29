package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.services.BookmarkServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/bookmark/storage")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkServiceManagement management;
    private final MessageMapper mapper;

    @PutMapping("/id/{id}")
    public ResponseEntity<?> add(@PathVariable("id") Long id, Authentication authentication) {
        if (authentication.getPrincipal() == null) {
            throw new RuntimeException();
        }
        BookmarkEntity response = management.add(id, UUID.fromString(authentication.getPrincipal().toString()));
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, Authentication authentication) {
        if (authentication.getPrincipal() == null) {
            throw new RuntimeException();
        }
        BookmarkEntity response = management.delete(id, UUID.fromString(authentication.getPrincipal().toString()));
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<?> showBookmarkById(@PathVariable("id") UUID uid, Pageable pageable) {
        Page<BookmarkEntity> responses = management.findById(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/user")
    public ResponseEntity<?> showBookmarkById(Authentication authentication, Pageable pageable) {
        if (authentication.getPrincipal() == null) {
            throw new RuntimeException();
        }
        Page<BookmarkEntity> responses = management.findById(UUID.fromString(authentication.getPrincipal().toString()), pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

}
