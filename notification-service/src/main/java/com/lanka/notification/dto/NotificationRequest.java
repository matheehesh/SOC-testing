package com.lanka.notification.dto;

public record NotificationRequest(String recipient, String channel, String type, String message) {
}
