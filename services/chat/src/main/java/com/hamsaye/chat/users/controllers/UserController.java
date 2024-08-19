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

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceManagement userServiceManagement;
    private final ResponseMessageMapper mapper;

    @MessageMapping("/user.disconnectUser")
    public ResponseEntity<?> disconnectUser(
            @Payload UUID uid,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        String sessionId = headerAccessor.getSessionId();
        UserResponse response = userServiceManagement.disconnectUser(uid, sessionId);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/user/id/{userId}")
    public @ResponseBody ResponseEntity<?> showUserById(@PathVariable("userId") UUID uid) {
        UserResponse response = userServiceManagement.findUserById(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
