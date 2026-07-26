package com.lanka.job.config;

import com.lanka.job.dto.JobRequest;
import com.lanka.job.repository.JobRepository;
import com.lanka.job.service.JobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JobSeeder implements CommandLineRunner {
    private final JobRepository jobs;
    private final JobService service;

    public JobSeeder(JobRepository jobs, JobService service) {
        this.jobs = jobs;
        this.service = service;
    }

    @Override
    public void run(String... args) {
        if (jobs.count() > 0) return;
        service.create(new JobRequest("3 Construction Workers", "Silva Constructions", "Construction", "Colombo", "Colombo City", 3, 2500, LocalDate.now().plusDays(1), true, "Heavy Lifting,Masonry", "Urgent construction support"));
        service.create(new JobRequest("Harvest Helpers x5", "Perera Farms", "Agriculture", "Gampaha", "Gampaha", 5, 1800, LocalDate.now().plusDays(2), false, "No Experience", "Harvest support"));
        service.create(new JobRequest("Delivery Driver", "FastCargo LK", "Driving", "Colombo", "Colombo City", 1, 3200, LocalDate.now().plusDays(1), true, "Driving License", "Own license required"));
        service.create(new JobRequest("Office Deep Cleaning x2", "CleanPro", "Cleaning", "Galle", "Galle", 2, 2000, LocalDate.now().plusDays(3), false, "Cleaning", "Office cleaning"));
        service.create(new JobRequest("Painting Team x4", "Rainbow Interiors", "Painting", "Kandy", "Kandy", 4, 2800, LocalDate.now().plusDays(4), false, "Painting", "Interior painting"));
        service.create(new JobRequest("Event Setup x6", "Golden Events", "Cleaning", "Colombo", "Colombo City", 6, 2200, LocalDate.now().plusDays(1), true, "Heavy Lifting", "Event setup crew"));
    }
}
