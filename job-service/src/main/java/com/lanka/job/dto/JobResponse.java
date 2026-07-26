package com.lanka.job.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record JobResponse(Long id, String title, String employer, String category, String district, String city,
                          Integer workersNeeded, Integer payPerWorker, LocalDate jobDate, String status, Boolean urgent,
                          Integer slotsRemaining, String requiredSkills, LocalDateTime createdAt) {
}
