package com.lanka.broker.dto;

public record OfflineWorkerResponse(Long id, String workerName, String workerNic, String district, String skills,
                                    String status, Integer totalJobs, Double rating, Long commissionEarned) {
}
