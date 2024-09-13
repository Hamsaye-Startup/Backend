package com.hamsaye.report.violations.controllers;

import com.hamsaye.report.applications.mapper.MessageMapper;
import com.hamsaye.report.violations.exceptions.AuthenticationCredentialNotFoundException;
import com.hamsaye.report.violations.models.ViolationResultStatus;
import com.hamsaye.report.violations.models.ViolationTypeCategory;
import com.hamsaye.report.violations.requests.ViolationRequest;
import com.hamsaye.report.violations.responses.DistributedViolationResponse;
import com.hamsaye.report.violations.responses.ViolationResponse;
import com.hamsaye.report.violations.responses.ViolationTypeResponse;
import com.hamsaye.report.violations.services.ViolationServiceManagement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/violation")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationServiceManagement violationServiceManagement;
    private final MessageMapper mapper;

    private UUID findUserByHeaderOrThrow(HttpServletRequest request) {
        String userId = request.getHeader("X_USER_ID");
        if (userId == null) {
            throw new AuthenticationCredentialNotFoundException("user id header not found");
        }
        return UUID.fromString(userId);
    }

    @PostMapping("/warehouse/{warehouseId}")
    public ResponseEntity<?> registerViolation(
            @PathVariable("warehouseId") Long warehouseId,
            @RequestBody ViolationRequest violationRequest,
            HttpServletRequest request
    ) {
        UUID response = violationServiceManagement.registerWarehouseViolation(
                violationRequest,
                warehouseId,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> registerViolation(
            @PathVariable("userId") UUID userId,
            @RequestBody ViolationRequest violationRequest,
            HttpServletRequest request
    ) {
        UUID response = violationServiceManagement.registerUserViolation(
                violationRequest,
                userId,
                findUserByHeaderOrThrow(request)
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<?> removeViolation(
            @PathVariable("code") UUID track
    ) {
        UUID response = violationServiceManagement.removeViolation(track);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findViolationByTrack(
            @PathVariable("code") UUID track
    ) {
        ViolationResponse response = violationServiceManagement.findViolationByTrack(track);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PostMapping(
            value = "/code/{code}/document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addViolationDocument(
            @PathVariable("code") UUID track,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        violationServiceManagement.addViolationDocument(
                track,
                multipartFile
        );
        return ResponseEntity.ok(mapper.toResponse());
    }

    @GetMapping("/agent/id/{id}")
    public ResponseEntity<?> findViolationsByAgent(
            @PathVariable("id") UUID agentId,
            Pageable pageable
    ) {
        Page<DistributedViolationResponse> responses =
                violationServiceManagement.findAllViolationsByAgentId(
                        agentId,
                        pageable
                );
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/code/{code}/start")
    public ResponseEntity<?> startViolation(
            @PathVariable("code") UUID track
    ) {
        ViolationResponse response = violationServiceManagement.startViolation(track);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping("/code/{code}/refer/agent/id/{id}")
    public ResponseEntity<?> referViolation(
            @PathVariable("code") UUID track,
            @PathVariable("id") UUID agentId
    ) {
        DistributedViolationResponse response = violationServiceManagement.changeViolationAgent(
                track,
                agentId
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @PutMapping("/code/{code}/result/{result}/finish")
    public ResponseEntity<?> finishViolation(
            @PathVariable("code") UUID track,
            @PathVariable("result") String result
    ) {
        DistributedViolationResponse response = violationServiceManagement.finishViolation(
                track,
                result
        );
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/code/{code}/document")
    public List<byte[]> downloadViolationDocuments(
            @PathVariable("code") UUID track
    ) {
        return violationServiceManagement.downloadViolationDocument(track);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> findAllViolationCategories() {

        List<ViolationTypeCategory> responses =
                violationServiceManagement.findAllViolationTypeCategories();
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/category/{category}/type")
    public ResponseEntity<?> findAllViolationTypesByCategory(
            @PathVariable("category") String category
    ) {
        List<ViolationTypeResponse> responses =
                violationServiceManagement.findAllViolationTypesByCategory(category);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @GetMapping("/code/{code}/dist")
    public ResponseEntity<?> findDistributedViolationByTrack(
            @PathVariable("code") UUID track
    ) {
        DistributedViolationResponse response =
                violationServiceManagement.findDistributedViolationByTrack(track);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/results")
    public ResponseEntity<?> findAllViolationResultStatus() {

        List<ViolationResultStatus> responses =
                violationServiceManagement.findAllViolationResultStatus();
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
