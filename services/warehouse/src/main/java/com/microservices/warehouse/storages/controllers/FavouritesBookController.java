package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.application.mapper.MessageMapper;
import com.microservices.warehouse.application.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.services.FavouritesBookServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This controller handles HTTP requests related to favorite books in storage.
 * It interacts with the {@link FavouritesBookServiceManagement} to perform operations on favorite books.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/storage/favourite")
@RequiredArgsConstructor
public class FavouritesBookController {

    /**
     * Service management for handling favorite book operations.
     * See {@link com.microservices.warehouse.storages.services.FavouritesBookServiceManagement} for more details.
     */
    private final FavouritesBookServiceManagement management;

    /**
     * Mapper for transforming between different representations of favorite books.
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
     * Adds a book to the user's list of favorite books.
     * @param id the ID of the storage book to add to favorites
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the added favorite book information
     * @since 1.0
     */
    @PutMapping("storage/id/{id}")
    public ResponseEntity<?> addFavouritesBook(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        FavouritesBookEntity response = management.addFavouritesBook(
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
     * Removes a book from the user's list of favorite books.
     * @param id the ID of the storage book to remove from favorites
     * @param request the HTTP request containing the user ID header
     * @return ResponseEntity containing the removed favorite book information
     * @since 1.0
     */
    @DeleteMapping("storage/id/{id}")
    public ResponseEntity<?> deleteFavouritesBook(
            @PathVariable("id") Long id,
            HttpServletRequest request
    ) {
        FavouritesBookEntity response = management.deleteFavouritesBookByStorageIdAndUserId(
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
     * Finds all favorite books for a specific user identified by user ID.
     * @param uid the UUID of the user to find favorite books for
     * @param pageable pagination information
     * @return ResponseEntity containing a page of favorite books for the user
     * @since 1.0
     */
    @GetMapping("/user/id/{id}")
    public ResponseEntity<?> findFavouritesBookById(
            @PathVariable("id") UUID uid,
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<FavouritesBookEntity> responses = management.findFavouritesBookByUserId(
                uid,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(
                responses,
                findUserByHeaderOrNull(request),
                request.getContextPath()
        ));
    }

    /**
     * Finds all favorite books for the user identified by the user ID in the request header.
     * @param request the HTTP request containing the user ID header
     * @param pageable pagination information
     * @return ResponseEntity containing a page of favorite books for the user
     * @since 1.0
     */
    @GetMapping("/user")
    public ResponseEntity<?> findFavouritesBookById(
            HttpServletRequest request,
            Pageable pageable
    ) {
        Page<FavouritesBookEntity> responses = management.findFavouritesBookByUserId(
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
