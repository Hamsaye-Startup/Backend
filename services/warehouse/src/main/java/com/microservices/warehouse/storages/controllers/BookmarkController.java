package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.application.mapper.MessageMapper;
import com.microservices.warehouse.application.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.models.BookmarkEntity;
import com.microservices.warehouse.storages.services.BookmarkServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This controller handles HTTP requests related to bookmarks for storages.
 * It interacts with the {@link BookmarkServiceManagement} to perform operations on bookmarks.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    /**
     * See {@link com.microservices.warehouse.storages.services.BookmarkServiceManagement} for more details.
     */
    private final BookmarkServiceManagement management;

    /**
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
     * Adds a bookmark for a storage item.
     * @param id the ID of the storage to bookmark
     * @param request the HTTP request containing user ID in headers
     * @return ResponseEntity containing the added bookmark information
     * @since 1.0
     */
    @PutMapping("storage/id/{id}")
    public ResponseEntity<?> addBookmark(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        BookmarkEntity response = management.addBookmark(
                id,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Deletes a bookmark for a storage item.
     * @param id the ID of the storage whose bookmark is to be deleted
     * @param request the HTTP request containing user ID in headers
     * @return ResponseEntity containing the deleted bookmark information
     * @since 1.0
     */
    @DeleteMapping("storage/id/{id}")
    public ResponseEntity<?> deleteBookmark(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        BookmarkEntity response = management.deleteBookmarkByStorageIdAndUserId(
                id,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(
                response,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds bookmarks for a specific user by their ID.
     * @param uid the ID of the user whose bookmarks are to be found
     * @param pageable pagination information
     * @return ResponseEntity containing a page of bookmarks for the user
     * @since 1.0
     */
    @GetMapping("/user/id/{id}")
    public ResponseEntity<?> findBookmarkById(
            @PathVariable("id") UUID uid,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<BookmarkEntity> responses = management.findBookmarkByUserId(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds bookmarks for the authenticated user.
     * @param request the HTTP request containing user ID in headers
     * @param pageable pagination information
     * @return ResponseEntity containing a page of bookmarks for the authenticated user
     * @since 1.0
     */
    @GetMapping("/user")
    public ResponseEntity<?> findBookmarkById(
            HttpServletRequest request,
            Pageable pageable
    ) {
        Page<BookmarkEntity> responses = management.findBookmarkByUserId(
                findUserByHeaderOrThrow(request),
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

}
