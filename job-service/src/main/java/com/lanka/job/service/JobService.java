package com.lanka.job.service;

import com.lanka.job.dto.JobRequest;
import com.lanka.job.dto.JobResponse;
import com.lanka.job.model.Job;
import com.lanka.job.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobs;

    public JobService(JobRepository jobs) {
        this.jobs = jobs;
    }

    public JobResponse create(JobRequest request) {
        Job job = new Job();
        job.setTitle(request.title());
        job.setEmployer(request.employer());
        job.setCategory(request.category());
        job.setDistrict(request.district());
        job.setCity(request.city());
        job.setWorkersNeeded(request.workersNeeded());
        job.setSlotsRemaining(request.workersNeeded());
        job.setPayPerWorker(request.payPerWorker());
        job.setJobDate(request.jobDate());
        job.setStatus("OPEN");
        job.setUrgent(Boolean.TRUE.equals(request.urgent()));
        job.setRequiredSkills(request.requiredSkills());
        job.setAdditionalNotes(request.additionalNotes());
        return toResponse(jobs.save(job));
    }

    public List<JobResponse> find(String district, String city, String category) {
        boolean hasDistrict = district != null && !district.isBlank();
        boolean hasCity = city != null && !city.isBlank();
        boolean hasCategory = category != null && !category.isBlank();
        List<Job> found;
        if (hasCity && hasCategory) found = jobs.findByCityAndCategoryAndStatus(city, category, "OPEN");
        else if (hasCity) found = jobs.findByCityAndStatus(city, "OPEN");
        else if (hasDistrict && hasCategory) found = jobs.findByDistrictAndCategoryAndStatus(district, category, "OPEN");
        else if (hasDistrict) found = jobs.findByDistrictAndStatus(district, "OPEN");
        else if (hasCategory) found = jobs.findByCategoryAndStatus(category, "OPEN");
        else found = jobs.findByStatus("OPEN");
        return found.stream().map(this::toResponse).toList();
    }

    public JobResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    public JobResponse updateStatus(Long id, String status) {
        Job job = findEntity(id);
        if (!List.of("OPEN", "CANCELLED", "COMPLETE", "COMPLETED", "FLAGGED").contains(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported job status");
        }
        job.setStatus(status);
        return toResponse(jobs.save(job));
    }

    public JobResponse flagJob(Long id) {
        Job job = findEntity(id);
        job.setStatus("FLAGGED");
        return toResponse(jobs.save(job));
    }

    private Job findEntity(Long id) {
        return jobs.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    private JobResponse toResponse(Job job) {
        return new JobResponse(job.getId(), job.getTitle(), job.getEmployer(), job.getCategory(), job.getDistrict(), job.getCity(), job.getWorkersNeeded(), job.getPayPerWorker(), job.getJobDate(), job.getStatus(), job.getUrgent(), job.getSlotsRemaining(), job.getRequiredSkills(), job.getCreatedAt());
    }
}
