package com.microservices.reservation.warehouse.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.warehouse.exceptions.AuthenticationCredentialNotFoundException;
import com.microservices.reservation.warehouse.requests.ReservationRequest;
import com.microservices.reservation.warehouse.requests.ReservationStrategyMode;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import com.microservices.reservation.warehouse.services.ReservationServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This controller handles HTTP requests related to reservations. It provides endpoints for creating,
 * updating, retrieving, and processing reservations within the warehouse reservation system.
 *
 * <p>The controller uses {@link ReservationServiceManagement} to process the business logic and
 * {@link MessageMapper} to convert service responses into API responses.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    /**
     * @see com.microservices.reservation.applications.mapper.MessageMapper
     */
    private final MessageMapper mapper;

    /**
     * @see com.microservices.reservation.warehouse.services.ReservationServiceManagement
     */
    private final ReservationServiceManagement management;

    /**
     * Finds the user ID from the request header.
     *
     * @param request the HTTP request
     * @return the user ID extracted from the header
     * @throws AuthenticationCredentialNotFoundException if the user ID header is not found
     * @since 1.0
     */
    private UUID findUserIdByHeader(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    /**
     * Creates a reservation based on the given mode and request data.
     *
     * @param mode the reservation strategy mode
     * @param reservation the reservation request data
     * @param request the HTTP request containing user ID in header
     * @return a {@link ResponseEntity} with the reservation response
     * @since 1.0
     */
    @PostMapping("/mode/{mode}")
    public ResponseEntity<?> reserve(
            @PathVariable("mode") ReservationStrategyMode mode,
            @RequestBody ReservationRequest reservation,
            HttpServletRequest request
    ) {
        ReservationResponse response = management.processReservation(
                mode,
                reservation,
                findUserIdByHeader(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Accepts a reservation by its ID.
     *
     * @param uid the reservation ID
     * @return a {@link ResponseEntity} with the updated reservation response
     * @since 1.0
     */
    @PutMapping("/id/{reservationId}/confirm/accept")
    public ResponseEntity<?> acceptReservation(
            @PathVariable("reservationId") UUID uid
    ) {
        ReservationResponse response = management.acceptReservation(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Rejects a reservation by its ID.
     *
     * @param uid the reservation ID
     * @return a {@link ResponseEntity} with the updated reservation response
     * @since 1.0
     */
    @PutMapping("/id/{reservationId}/confirm/reject")
    public ResponseEntity<?> rejectReservation(
            @PathVariable("reservationId") UUID uid
    ) {
        ReservationResponse response = management.rejectReservation(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Retrieves reservations for a specific warehouse.
     *
     * @param id the warehouse ID
     * @param pageable pagination information
     * @return a {@link ResponseEntity} with a page of reservation responses
     * @since 1.0
     */
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<?> findReservationsByWarehouse(
            @PathVariable("id") Long id, Pageable pageable
    ) {
        Page<ReservationResponse> responses = management.showReservationByWarehouse(id, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Retrieves reservations for a specific host.
     *
     * @param uid the host ID
     * @param pageable pagination information
     * @return a {@link ResponseEntity} with a page of reservation responses
     * @since 1.0
     */
    @GetMapping("/warehouse/host/{id}")
    public ResponseEntity<?> findReservationsByHost(
            @PathVariable("id") UUID uid, Pageable pageable
    ) {
        Page<ReservationResponse> responses = management.showReservationByHost(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Retrieves reservations for a specific renter.
     *
     * @param uid the renter ID
     * @param pageable pagination information
     * @return a {@link ResponseEntity} with a page of reservation responses
     * @since 1.0
     */
    @GetMapping("/warehouse/renter/{id}")
    public ResponseEntity<?> findReservationsByRenter(
            @PathVariable("id") UUID uid, Pageable pageable
    ) {
        Page<ReservationResponse> responses = management.showReservationByRenter(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Retrieves a reservation by its ID and strategy mode.
     *
     * @param mode the reservation strategy mode
     * @param uid the reservation ID
     * @return a {@link ResponseEntity} with the reservation response
     * @since 1.0
     */
    @GetMapping("/mode/{mode}/id/{id}")
    public ResponseEntity<?> findReservationById(
            @PathVariable("mode") ReservationStrategyMode mode,
            @PathVariable("id") UUID uid) {

        ReservationResponse response = management.processFindingById(mode, uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
