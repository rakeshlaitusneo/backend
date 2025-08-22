package com.laitusneo.backend.service;

import com.laitusneo.backend.entity.EmailNotification;
import com.laitusneo.backend.repository.EmailNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;

    public EmailNotificationService(EmailNotificationRepository emailNotificationRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
    }

    public EmailNotification logNotification(EmailNotification notification) {
        return emailNotificationRepository.save(notification);
    }

    public List<EmailNotification> getAllNotifications() {
        return emailNotificationRepository.findAll();
    }

    public EmailNotification getNotificationById(Long id) {
        return emailNotificationRepository.findById(id).orElse(null);
    }

    public List<EmailNotification> getNotificationsByRecipient(String recipientEmail) {
        return emailNotificationRepository.findByRecipientEmail(recipientEmail);
    }

    public List<EmailNotification> getNotificationsByStatus(EmailNotification.SentStatus status) {
        return emailNotificationRepository.findBySentStatus(status);
    }

    public void deleteNotification(Long id) {
        emailNotificationRepository.deleteById(id);
    }
}
