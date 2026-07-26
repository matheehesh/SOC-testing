package com.lanka.job.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String employer;
    private String category;
    private String district;
    private String city;
    private Integer workersNeeded;
    private Integer payPerWorker;
    private LocalDate jobDate;
    private String status = "OPEN";
    private Boolean urgent = false;
    private Integer slotsRemaining;
    private String requiredSkills;
    private String additionalNotes;
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (status == null) status = "OPEN";
        if (urgent == null) urgent = false;
        if (slotsRemaining == null) slotsRemaining = workersNeeded;
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getWorkersNeeded() {
        return workersNeeded;
    }

    public void setWorkersNeeded(Integer workersNeeded) {
        this.workersNeeded = workersNeeded;
    }

    public Integer getPayPerWorker() {
        return payPerWorker;
    }

    public void setPayPerWorker(Integer payPerWorker) {
        this.payPerWorker = payPerWorker;
    }

    public LocalDate getJobDate() {
        return jobDate;
    }

    public void setJobDate(LocalDate jobDate) {
        this.jobDate = jobDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public Integer getSlotsRemaining() {
        return slotsRemaining;
    }

    public void setSlotsRemaining(Integer slotsRemaining) {
        this.slotsRemaining = slotsRemaining;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
