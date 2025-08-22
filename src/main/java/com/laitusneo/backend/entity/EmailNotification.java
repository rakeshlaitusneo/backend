package com.laitusneo.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private String subject;

    @Lob
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(name = "sent_status")
    private SentStatus sentStatus = SentStatus.SENT;

    @Column(name = "sent_at")
    private LocalDateTime sentAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private RelatedEntityType relatedEntityType;

    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    public enum SentStatus {
        SENT, FAILED
    }

    public enum RelatedEntityType {
        LEAVE_REQUEST, SALARY, ATTENDANCE, OTHER
    }
}
