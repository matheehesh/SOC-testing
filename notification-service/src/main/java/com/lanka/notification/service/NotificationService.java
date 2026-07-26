package com.lanka.notification.service;

import com.lanka.notification.dto.NotificationRequest;
import com.lanka.notification.model.Notification;
import com.lanka.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notifications;

    public NotificationService(NotificationRepository notifications) {
        this.notifications = notifications;
    }

    public Notification send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipient(request.recipient());
        notification.setChannel(request.channel() == null ? "EMAIL" : request.channel());
        notification.setType(request.type() == null ? "GENERAL" : request.type());
        notification.setMessage(request.message());
        notification.setStatus("SENT");
        notification.setSentAt(LocalDateTime.now());
        log.info("[SMS MOCK] To: {} | {}", request.recipient(), request.message());
        return notifications.save(notification);
    }

    public List<Notification> list() {
        return notifications.findAll();
    }

    public List<Notification> listByRecipient(String recipient) {
        return notifications.findByRecipient(recipient);
    }
}
