package com.laitusneo.backend.controller;

import com.laitusneo.backend.entity.EmailNotification;
import com.laitusneo.backend.service.EmailNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-notifications")
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    public EmailNotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    // Create a log entry (when email is sent)
    @PostMapping
    public ResponseEntity<EmailNotification> createNotification(@RequestBody EmailNotification notification) {
        return ResponseEntity.ok(emailNotificationService.createNotification(notification));
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<EmailNotification>> getAllNotifications() {
        return ResponseEntity.ok(emailNotificationService.getAllNotifications());
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmailNotification> getNotificationById(@PathVariable Long id) {
        return emailNotificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get notifications by recipient email
    @GetMapping("/recipient/{email}")
    public ResponseEntity<List<EmailNotification>> getNotificationsByRecipient(@PathVariable String email) {
        return ResponseEntity.ok(emailNotificationService.getNotificationsByRecipient(email));
    }

    // Delete notification log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        emailNotificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}

