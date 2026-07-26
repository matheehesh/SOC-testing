package com.lanka.notification.controller;

import com.lanka.notification.dto.NotificationRequest;
import com.lanka.notification.model.Notification;
import com.lanka.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    Notification send(@RequestBody NotificationRequest request) {
        return service.send(request);
    }

    @GetMapping
    List<Notification> list() {
        return service.list();
    }

    @GetMapping("/{recipient}")
    List<Notification> listByRecipient(@PathVariable String recipient) {
        return service.listByRecipient(recipient);
    }
}
