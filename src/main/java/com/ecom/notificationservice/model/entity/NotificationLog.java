package com.ecom.notificationservice.model.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import com.ecom.notificationservice.model.enums.NotificationStatus;
import com.ecom.notificationservice.model.enums.NotificationType;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLog {

    @Id
    private String id;

    private String toEmail;

    private String subject;

    private String message;

    private NotificationType type;

    private NotificationStatus status;

    private Instant sentAt;

}
