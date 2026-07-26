package com.lanka.job.controller;

import com.lanka.job.dto.JobRequest;
import com.lanka.job.dto.JobResponse;
import com.lanka.job.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping
    JobResponse create(@RequestBody JobRequest request) {
        return service.create(request);
    }

    @GetMapping
    List<JobResponse> list(@RequestParam(required = false) String district, @RequestParam(required = false) String city,
                           @RequestParam(required = false) String category) {
        return service.find(district, city, category);
    }

    @GetMapping("/{id}")
    JobResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/status")
    JobResponse updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @PutMapping("/{id}/flag")
    JobResponse flag(@PathVariable Long id) {
        return service.flagJob(id);
    }
}
