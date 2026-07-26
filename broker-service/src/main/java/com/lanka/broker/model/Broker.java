package com.lanka.broker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Broker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nic;
    private String phone;
    private String email;
    private String password;
    private String brokerId;
    private String district;
    private String city;
    private String status = "PENDING";
    private String yearsExperience;
    private String estimatedWorkers;
    private String workerMethod;
    private Integer totalWorkers = 0;
    private Long commissionMTD = 0L;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;

    @PrePersist
    void prePersist() {
        if (status == null) status = "PENDING";
        if (totalWorkers == null) totalWorkers = 0;
        if (commissionMTD == null) commissionMTD = 0L;
        if (submittedAt == null) submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(String yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getEstimatedWorkers() {
        return estimatedWorkers;
    }

    public void setEstimatedWorkers(String estimatedWorkers) {
        this.estimatedWorkers = estimatedWorkers;
    }

    public String getWorkerMethod() {
        return workerMethod;
    }

    public void setWorkerMethod(String workerMethod) {
        this.workerMethod = workerMethod;
    }

    public Integer getTotalWorkers() {
        return totalWorkers;
    }

    public void setTotalWorkers(Integer totalWorkers) {
        this.totalWorkers = totalWorkers;
    }

    public Long getCommissionMTD() {
        return commissionMTD;
    }

    public void setCommissionMTD(Long commissionMTD) {
        this.commissionMTD = commissionMTD;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}
