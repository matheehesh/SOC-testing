package com.lanka.job.dto;

import java.time.LocalDate;

public record JobRequest(String title, String employer, String category, String district, String city,
                         Integer workersNeeded, Integer payPerWorker, LocalDate jobDate, Boolean urgent,
                         String requiredSkills, String additionalNotes) {
}
