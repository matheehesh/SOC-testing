package com.lanka.broker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OfflineWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brokerId;
    private Long brokerEntityId;
    private String workerName;
    private String workerNic;
    private String district;
    private String city;
    private String skills;
    private String availability;
    private String status = "ACTIVE";
    private Integer totalJobs = 0;
    private Double rating = 0.0;
    private Long commissionEarned = 0L;
    private LocalDateTime registeredAt;

    @PrePersist
    void prePersist() {
        if (status == null) status = "ACTIVE";
        if (totalJobs == null) totalJobs = 0;
        if (rating == null) rating = 0.0;
        if (commissionEarned == null) commissionEarned = 0L;
        if (registeredAt == null) registeredAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public Long getBrokerEntityId() {
        return brokerEntityId;
    }

    public void setBrokerEntityId(Long brokerEntityId) {
        this.brokerEntityId = brokerEntityId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerNic() {
        return workerNic;
    }

    public void setWorkerNic(String workerNic) {
        this.workerNic = workerNic;
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Integer totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getCommissionEarned() {
        return commissionEarned;
    }

    public void setCommissionEarned(Long commissionEarned) {
        this.commissionEarned = commissionEarned;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
