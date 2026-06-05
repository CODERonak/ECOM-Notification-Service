package com.ecom.notificationservice.dto;

import com.ecom.notificationservice.model.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String toEmail;

    private String subject;

    private String message;

    private NotificationType type; 
}
