package com.ecom.notificationservice.repository;

import com.ecom.notificationservice.model.entity.NotificationLog;
import com.ecom.notificationservice.model.enums.NotificationType;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {
    List<NotificationLog> findByToEmail(String email);
    List<NotificationLog> findByType(NotificationType type);
}
