package com.lanka.broker.dto;

public record BrokerRequest(String name, String nic, String phone, String email, String password, String district,
                            String city, String yearsExperience, String estimatedWorkers, String workerMethod) {
}
