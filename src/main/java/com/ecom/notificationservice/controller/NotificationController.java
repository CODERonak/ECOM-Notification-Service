package com.ecom.notificationservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecom.notificationservice.dto.NotificationRequest;
import com.ecom.notificationservice.model.entity.NotificationLog;
import com.ecom.notificationservice.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Service", description = "Endpoints for sending emails and audit logging")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send notification", description = "Sends an email and logs the attempt.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification processed successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to send email via SMTP")
    })
    public ResponseEntity<String> sendNotification(@RequestBody @Valid NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/history/{email}")
    @Operation(summary = "Get user history", description = "Retrieves all notification logs for a specific user email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "History retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<NotificationLog>> getHistory(@PathVariable String email) {
        return ResponseEntity.ok(notificationService.getHistoryForUser(email));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all logs", description = "Admin endpoint to view all notification logs.")
    public ResponseEntity<List<NotificationLog>> getAllLogs() {
        return ResponseEntity.ok(notificationService.getAllLogs());
    }
}