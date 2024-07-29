package com.microservices.user.users.controllers;

import com.microservices.user.users.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user/details")
@RequiredArgsConstructor
public class UserDetailsController {

    private final UserDetailsServiceImpl service;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") UUID uid) {
        return ResponseEntity.ok(service.loadUserByUsername(uid.toString()));
    }
}
