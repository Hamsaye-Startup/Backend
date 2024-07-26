package com.microservices.user.passwords.controllers;

import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.passwords.requests.PasswordRequest;
import com.microservices.user.passwords.services.PasswordServiceManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/pass")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordServiceManagement management;
    private final MessageMapper mapper;

    @PreAuthorize("hasAuthority('UPDATE_PASS')")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody PasswordRequest request, Principal principal) {
        management.update(request, principal);
        return new ResponseEntity<>(mapper.toResponse(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('RESET_PASS')")
    @PostMapping("/reset/id/{id}")
    public ResponseEntity<?> reset(@PathVariable("id") UUID uid) {

        // TODO: change the password
        // TODO: send code by the sms
        return null;
    }
}
