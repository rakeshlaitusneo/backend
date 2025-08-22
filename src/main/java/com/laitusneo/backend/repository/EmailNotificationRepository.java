package com.laitusneo.backend.repository;

import com.laitusneo.backend.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
    List<EmailNotification> findByRecipientEmail(String recipientEmail);
    List<EmailNotification> findBySentStatus(EmailNotification.SentStatus status);
}
