package com.ecom.notificationservice.service;

import java.time.Instant;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ecom.notificationservice.dto.NotificationRequest;
import com.ecom.notificationservice.exception.custom.*;
import com.ecom.notificationservice.model.entity.NotificationLog;
import com.ecom.notificationservice.model.enums.NotificationStatus;
import com.ecom.notificationservice.repository.NotificationLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationLogRepository notificationRepository;
    private final JavaMailSender mailSender;

    public String sendNotification(NotificationRequest request) {
        if (request.getToEmail() == null || request.getToEmail().isBlank()) {
            throw new NotificationValidationException("Recipient email address cannot be null or empty.");
        }

        NotificationLog notifLog = NotificationLog.builder()
                .toEmail(request.getToEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .type(request.getType())
                .status(NotificationStatus.PENDING)
                .sentAt(Instant.now())
                .build();

        notificationRepository.save(notifLog);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(request.getToEmail());
            mailMessage.setSubject(request.getSubject());
            mailMessage.setText(request.getMessage());
            mailMessage.setFrom("${FROM_MAIL}");

            mailSender.send(mailMessage);

            notifLog.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notifLog);

            return "Notification sent successfully";

        } catch (NotificationSendingException e) {
            log.error("Failed to send email to {}", request.getToEmail(), e);

            notifLog.setStatus(NotificationStatus.FAILED);
            notificationRepository.save(notifLog);

            throw new NotificationSendingException("Failed to send email: " + e.getMessage());
        }
    }

    public List<NotificationLog> getHistoryForUser(String email) {
        List<NotificationLog> logs = notificationRepository.findByToEmail(email);
        if (logs.isEmpty()) {
            throw new NotificationNotFoundException("No notification history found for email: " + email);
        }
        return logs;
    }

    public List<NotificationLog> getAllLogs() {
        return notificationRepository.findAll();
    }
}