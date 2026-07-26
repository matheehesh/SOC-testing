package com.lanka.broker.dto;

import java.time.LocalDateTime;

public record BrokerResponse(Long id, String brokerId, String name, String district, String city, String status,
                             String phone, Integer totalWorkers, Long commissionMTD, LocalDateTime submittedAt) {
}
