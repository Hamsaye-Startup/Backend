package com.hamsaye.chat.users.controllers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.users.responses.UserResponse;
import com.hamsaye.chat.users.services.UserServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Controller for handling user-related requests and interactions via WebSocket and HTTP.
 * <p>
 * This controller provides endpoints for disconnecting users through WebSocket messages and
 * retrieving user information via HTTP GET requests.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    /**
     * @see com.hamsaye.chat.users.services.UserServiceManagement
     */
    private final UserServiceManagement userServiceManagement;

    /**
     * @see com.hamsaye.chat.applications.mapper.ResponseMessageMapper
     */
    private final ResponseMessageMapper mapper;

    /**
     * Handles WebSocket messages to disconnect a user based on their user ID.
     * <p>
     * This method listens for messages sent to the "/user.disconnectUser" endpoint and
     * disconnects the specified user from the WebSocket session.
     * </p>
     *
     * @param uid the UUID of the user to disconnect
     * @param headerAccessor provides access to WebSocket message headers, including the session ID
     * @return a {@link ResponseEntity} with the updated {@link UserResponse}
     */
    @MessageMapping("/user.disconnectUser")
    public ResponseEntity<?> disconnectUser(
            @Payload UUID uid,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        String sessionId = headerAccessor.getSessionId();
        UserResponse response = userServiceManagement.disconnectUser(uid, sessionId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Retrieves user information based on the provided user ID via an HTTP GET request.
     * <p>
     * This method handles requests to the "/user/id/{userId}" endpoint and returns the
     * {@link UserResponse} of the specified user.
     * </p>
     *
     * @param uid the UUID of the user to retrieve
     * @return a {@link ResponseEntity} with the {@link UserResponse} of the specified user
     */
    @GetMapping("/user/id/{userId}")
    public @ResponseBody ResponseEntity<?> showUserById(@PathVariable("userId") UUID uid) {
        UserResponse response = userServiceManagement.findUserById(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
