package com.microservices.warehouse.storages.controllers;

import com.microservices.warehouse.applications.mapper.MessageMapper;
import com.microservices.warehouse.storages.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.warehouse.storages.models.FavouritesBookEntity;
import com.microservices.warehouse.storages.services.FavouritesBookServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/storage/favourite")
@RequiredArgsConstructor
public class FavouritesBookController {

    private final FavouritesBookServiceManagement management;
    private final MessageMapper mapper;

    private UUID findUserByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    @PutMapping("storage/id/{id}")
    public ResponseEntity<?> addFavouritesBook(
            @PathVariable("id") Long id, 
            HttpServletRequest request
    ) {
        FavouritesBookEntity response = management.addFavouritesBook(
                id, 
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("storage/id/{id}")
    public ResponseEntity<?> deleteFavouritesBook(
            @PathVariable("id") Long id, 
            HttpServletRequest request
    ) {
        FavouritesBookEntity response = management.deleteFavouritesBookByStorageIdAndUserId(
                id, 
                findUserByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<?> findFavouritesBookById(
            @PathVariable("id") UUID uid,
            Pageable pageable
    ) {
        Page<FavouritesBookEntity> responses = management.findFavouritesBookByUserId(
                uid,
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/user")
    public ResponseEntity<?> findFavouritesBookById(
            HttpServletRequest request,
            Pageable pageable
    ) {
        Page<FavouritesBookEntity> responses = management.findFavouritesBookByUserId(
                findUserByHeader(request),
                pageable
        );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

}
